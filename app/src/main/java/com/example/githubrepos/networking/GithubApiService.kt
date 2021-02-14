package com.example.githubrepos.networking

import com.example.githubrepos.model.remote.contributorresponse.ContributorResponse
import com.example.githubrepos.model.remote.issuesresponse.IssuesResponse
import com.example.githubrepos.model.remote.repositoryresponse.RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

// Example Apis

//https://api.github.com/search/repositories?q=kotlin%20language:kotlin&page=1&per_page=100


//https://api.github.com/repos/Kotlin/kotlin-koans/issues

//https://api.github.com/repos/Kotlin/kotlin-koans/contributors


interface GithubApiService {

    @GET("search/repositories")
    suspend fun getRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): RepositoryResponse


    @GET
    suspend fun getIssues(
        @Url url: String
    ): IssuesResponse

    @GET
    suspend fun getContributor(
        @Url url: String
    ): ContributorResponse
}