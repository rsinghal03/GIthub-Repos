package com.example.githubrepos.ui.repository.repositorylist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.githubrepos.R
import com.example.githubrepos.databinding.FragmentRepositoryListBinding
import com.example.githubrepos.ui.base.BaseFragment
import com.example.githubrepos.ui.repository.RepositoryViewModel
import com.example.githubrepos.util.REPOSITORY_CONTRIBUTOR_URL
import com.example.githubrepos.util.REPOSITORY_DESCRIPTION
import com.example.githubrepos.util.REPOSITORY_ISSUES_URL
import com.example.githubrepos.util.REPOSITORY_NAME
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val DEFAULT_QUERY = "Kotlin"

class RepositoryListFragment : BaseFragment<FragmentRepositoryListBinding, RepositoryViewModel>() {

    private val repositoryViewModel: RepositoryViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.fragment_repository_list

    override fun getViewModel(): RepositoryViewModel = repositoryViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    private val repositoryListAdapter: RepositoryListAdapter by lazy { RepositoryListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        repositoryViewModel.onSearchClick().invoke(query)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
    }

    /**
     * Initialise adapter
     *
     */
    private fun initAdapter() {
        setRecyclerAttr()
        listItemClickListener()
        addLoadStateListener()
        registerProgressBar()
        observePagingData()
    }

    /**
     * Set recycler view property
     *
     */
    private fun setRecyclerAttr() {
        viewDataBinding?.list?.run {
            adapter = repositoryListAdapter
            setHasFixedSize(true)
            adapter = repositoryListAdapter.withLoadStateFooter(
                footer = RepositoryListLoadStateAdapter { repositoryListAdapter.retry() }
            )
        }
    }

    /**
     * Observe paging data source and submit data to adapter
     *
     */
    private fun observePagingData() {
        repositoryViewModel.subscribeToPagingData().observe(viewLifecycleOwner, {
            repositoryListAdapter.submitData(lifecycle, it)
        })
    }

    /**
     * Show progress bar when Load state is Loading
     *
     */
    private fun registerProgressBar() {
        lifecycleScope.launchWhenCreated {
            repositoryListAdapter.loadStateFlow.collectLatest { loadStates ->
                viewDataBinding?.swipeRefresh?.isRefreshing =
                    loadStates.refresh is LoadState.Loading
            }
        }
    }

    /**
     * Add load state listener to show error state of the paging source data
     *
     */
    private fun addLoadStateListener() {
        repositoryListAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        (it.refresh as LoadState.Error).error.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }
        }
    }

    /**
     * on list item click navigate to details fragment [com.example.githubrepos.ui.repository.repositorydetails.RepositoryDetailsFragment]
     *
     */
    private fun listItemClickListener() {
        repositoryListAdapter.itemClickListener = {
            val bundle = bundleOf(
                REPOSITORY_NAME to it?.name,
                REPOSITORY_DESCRIPTION to it?.description,
                REPOSITORY_CONTRIBUTOR_URL to it?.contributors_url,
                REPOSITORY_ISSUES_URL to it?.issues_url
            )
            findNavController().navigate(
                R.id.action_repositoryListFragment_to_repositoryDetailsFragment,
                bundle
            )
        }
    }

    /**
     * sets the refresh listener
     *
     */
    private fun initSwipeToRefresh() {
        viewDataBinding?.swipeRefresh?.setOnRefreshListener { repositoryListAdapter.refresh() }
    }
}