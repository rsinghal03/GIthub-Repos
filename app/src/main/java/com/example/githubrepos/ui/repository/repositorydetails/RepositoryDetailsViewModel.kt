package com.example.githubrepos.ui.repository.repositorydetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubrepos.extension.replaceNumber
import com.example.githubrepos.model.local.RepositoryDetails
import com.example.githubrepos.repository.GithubRepoRepository
import com.example.githubrepos.ui.base.BaseViewModel
import com.example.githubrepos.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class RepositoryDetailsViewModel(private val githubRepoRepository: GithubRepoRepository) :
    BaseViewModel() {

    val repositoryDetails = MutableLiveData<Resource<RepositoryDetails>>()

    fun findDetailsOfRepo(contributorUrl: String?, issuesUrl: String?) {
        viewModelScope.launch {
            val contributorsJob = ioDispatcher.async {
                githubRepoRepository.getContributor(contributorUrl)
            }

            val issuesJob = ioDispatcher.async {
                githubRepoRepository.getIssues(issuesUrl?.replaceNumber())
            }
            try {
                val contributorResponse = contributorsJob.await()
                val issuesResponse = issuesJob.await()

                val listOfContri = contributorResponse?.take(6)?.map { it.login }
                val listOfIssues = issuesResponse?.take(6)?.map { it.title }

                repositoryDetails.postValue(
                    Resource.Success(
                        RepositoryDetails(
                            listOfIssues,
                            listOfContri
                        )
                    )
                )
            } catch (e: Exception) {
                repositoryDetails.postValue(Resource.Error(e.message))
                Timber.e(e)
            }
        }
    }
}