package com.example.githubrepos.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.githubrepos.networking.GithubApiService

class GithubRepoRepository(private val githubApiService: GithubApiService) {

    fun getRepos(query: String, pageSize: Int) = Pager(
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
    }.flow

    suspend fun getIssues(url: String?) = url?.let { githubApiService.getIssues(it) }


    suspend fun getContributor(url: String?) = url?.let { githubApiService.getContributor(it) }

}
