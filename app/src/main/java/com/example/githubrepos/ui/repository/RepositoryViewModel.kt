package com.example.githubrepos.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.githubrepos.model.remote.repositoryresponse.Item
import com.example.githubrepos.repository.GithubRepoRepository
import com.example.githubrepos.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Repository view model
 *
 * @property githubRepoRepository [GithubRepoRepository]
 */
class RepositoryViewModel(private val githubRepoRepository: GithubRepoRepository) :
    BaseViewModel() {

    /**
     * On search click initiate data fetch from the api
     *
     */
    fun onSearchClick() = { query: String ->
        if (query.isNotBlank()) {
            viewModelScope.launch {
                githubRepoRepository.getRepos(query = query, 20, viewModelScope)
            }
        }
    }

    /**
     * Subscribe to paging data source
     *
     */
    fun subscribeToPagingData(): LiveData<PagingData<Item>> {
        return githubRepoRepository.flow.asLiveData()
    }

}