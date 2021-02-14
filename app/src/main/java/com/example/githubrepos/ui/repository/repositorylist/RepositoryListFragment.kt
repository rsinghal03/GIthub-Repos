package com.example.githubrepos.ui.repository.repositorylist

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
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
        repositoryViewModel.searchRepo(query)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
        initSearch()
    }

    private fun initAdapter() {
        viewDataBinding?.list?.run {
            adapter = repositoryListAdapter
            setHasFixedSize(true)
            adapter = repositoryListAdapter.withLoadStateFooter(
                footer = RepositoryListLoadStateAdapter { repositoryListAdapter.retry() }
            )
        }

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

        lifecycleScope.launchWhenCreated {
            repositoryListAdapter.loadStateFlow.collectLatest { loadStates ->
                viewDataBinding?.swipeRefresh?.isRefreshing =
                    loadStates.refresh is LoadState.Loading
            }
        }

        repositoryViewModel.getGithubRepo.observe(viewLifecycleOwner, {
            repositoryListAdapter.submitData(lifecycle, it)
        })

        lifecycleScope.launchWhenCreated {
            repositoryListAdapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { viewDataBinding?.list?.scrollToPosition(0) }
        }
    }

    private fun initSwipeToRefresh() {
        viewDataBinding?.swipeRefresh?.setOnRefreshListener { repositoryListAdapter.refresh() }
    }

    private fun initSearch() {
        viewDataBinding?.searchRepo?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEARCH) {
                getListOfRepo()
                (getSystemService(
                    requireContext(),
                    InputMethodManager::class.java
                ) as InputMethodManager).hideSoftInputFromWindow(
                    viewDataBinding?.root?.windowToken,
                    0
                )
                true
            } else {
                false
            }
        }
        viewDataBinding?.searchRepo?.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                getListOfRepo()
                true
            } else {
                false
            }
        }
    }

    private fun getListOfRepo() {
        viewDataBinding?.searchRepo?.text?.trim()?.toString()?.let {
            if (it.isNotBlank()) {
                repositoryViewModel.searchRepo(it)
            }
        }
    }
}