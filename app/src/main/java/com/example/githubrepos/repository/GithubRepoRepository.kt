package com.example.githubrepos.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubrepos.model.remote.issuesresponse.IssuesResponse
import com.example.githubrepos.model.remote.repositoryresponse.Item
import com.example.githubrepos.networking.GithubApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

class GithubRepoRepository(
    private val githubApiService: GithubApiService
) {

    val flow = MutableSharedFlow<PagingData<Item>>(replay = 1)

    suspend fun getRepos(query: String, pageSize: Int, viewModelScope: CoroutineScope) {
        Pager(
            PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            )
        )
        {
            PageKeyedGithubRepoPagingSource(
                githubApiService = githubApiService,
                query,
                pageSize
            )
        }.flow.cachedIn(viewModelScope).collectLatest {
            flow.emit(it)
        }
    }

    suspend fun getIssues(url: String?): IssuesResponse? {
        return url?.let { githubApiService.getIssues(it) }
    }

    suspend fun getContributor(url: String?) = url?.let { githubApiService.getContributor(it) }

}
