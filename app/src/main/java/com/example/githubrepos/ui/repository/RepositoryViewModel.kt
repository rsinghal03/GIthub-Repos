package com.example.githubrepos.ui.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubrepos.model.remote.repositoryresponse.Item
import com.example.githubrepos.repository.GithubRepoRepository
import com.example.githubrepos.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class RepositoryViewModel(private val githubRepoRepository: GithubRepoRepository) :
    BaseViewModel() {


    val getGithubRepo = MutableLiveData<PagingData<Item>>()
    private val queryLiveData = MutableLiveData<String>()

    fun searchRepo(query: String) {
        queryLiveData.value = query
        viewModelScope.launch {
            githubRepoRepository.getRepos(query = query, 20).cachedIn(this).collect {
                getGithubRepo.value = it
            }
        }
    }

    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared of Viewmodel")
    }
}