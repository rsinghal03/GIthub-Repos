package com.example.githubrepos.model.remote.repositoryresponse

data class RepositoryResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)