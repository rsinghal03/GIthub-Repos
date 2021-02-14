package com.example.githubrepos.ui.repository.repositorydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.example.githubrepos.R
import com.example.githubrepos.databinding.FragmentRepositoryDetailsBinding
import com.example.githubrepos.databinding.RepositoryDetailsItemBinding
import com.example.githubrepos.model.local.RepositoryDetails
import com.example.githubrepos.ui.base.BaseFragment
import com.example.githubrepos.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryDetailsFragment :
    BaseFragment<FragmentRepositoryDetailsBinding, RepositoryDetailsViewModel>() {

    private val repositoryDetailsViewModel: RepositoryDetailsViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.fragment_repository_details

    override fun getViewModel(): RepositoryDetailsViewModel = repositoryDetailsViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    private var repoName: String? = null
    private var repoDes: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        val contributorUrl = requireArguments().getString(REPOSITORY_CONTRIBUTOR_URL)
        val issuesUrl = requireArguments().getString(REPOSITORY_ISSUES_URL)
        repoName = requireArguments().getString(REPOSITORY_NAME)
        repoDes = requireArguments().getString(REPOSITORY_DESCRIPTION)
        setLoader(true)
        repositoryDetailsViewModel.findDetailsOfRepo(contributorUrl, issuesUrl)
    }

    private fun initObserver() {
        repositoryDetailsViewModel.repositoryDetails.observe(viewLifecycleOwner, {
            setLoader(false)
            when (it) {
                is Resource.Success -> {
                    bindData(it.data)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> Unit
            }
        })
    }

    private fun bindData(data: RepositoryDetails?) {
        viewDataBinding?.run {
            detailsIssuesHeader.text = requireContext().resources.getString(R.string.issues_label)
            detailsContributorHeader.text =
                requireContext().resources.getString(R.string.contributor_label)
            detailsRepoName.text = repoName
            detailsRepoDescription.text = repoDes
        }
        data?.listOfContributor?.forEach {
            val binding: RepositoryDetailsItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(requireContext()), R.layout.repository_details_item,
                viewDataBinding?.root as ViewGroup?, false
            )
            viewDataBinding?.detailsContributor?.addView(binding.root)
            binding.detailsItem.text = it
        }

        data?.listOfIssues?.forEach {
            val binding: RepositoryDetailsItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(requireContext()), R.layout.repository_details_item,
                viewDataBinding?.root as ViewGroup?, false
            )
            viewDataBinding?.detailsIssues?.addView(binding.root)
            binding.detailsItem.text = it
        }
    }
}