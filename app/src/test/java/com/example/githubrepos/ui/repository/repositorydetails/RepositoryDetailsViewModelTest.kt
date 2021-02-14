package com.example.githubrepos.ui.repository.repositorydetails

import com.example.githubrepos.BaseTest
import com.example.githubrepos.model.remote.contributorresponse.ContributorResponse
import com.example.githubrepos.model.remote.issuesresponse.IssuesResponse
import com.example.githubrepos.repository.GithubRepoRepository
import com.example.githubrepos.util.Resource
import com.example.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.core.inject
import org.mockito.ArgumentMatchers.anyString


@ExperimentalCoroutinesApi
class RepositoryDetailsViewModelTest : BaseTest() {

    private val repositoryDetailsViewModel: RepositoryDetailsViewModel by inject()
    private val githubRepoRepository: GithubRepoRepository by inject()

    @Test
    fun getRepositoryDetails_success() {
        val contributorResponse = mockk<ContributorResponse>(relaxed = true)
        val issuesResponse = mockk<IssuesResponse>(relaxed = true)
        coEvery { githubRepoRepository.getIssues(anyString()) } returns issuesResponse
        coEvery { githubRepoRepository.getContributor(anyString()) } returns contributorResponse
        repositoryDetailsViewModel.findDetailsOfRepo(anyString(), anyString())
        val expected = listOf<String>()
        assertEquals(
            expected,
            repositoryDetailsViewModel.repositoryDetails.getOrAwaitValue().data?.listOfIssues
        )
        assertEquals(
            expected,
            repositoryDetailsViewModel.repositoryDetails.getOrAwaitValue().data?.listOfContributor
        )
    }

    @Test
    fun getRepositoryDetails_error() {
        repositoryDetailsViewModel.findDetailsOfRepo(anyString(), anyString())
        val expected = Resource.Error::class.java
        assertEquals(
            expected,
            repositoryDetailsViewModel.repositoryDetails.getOrAwaitValue()::class.java
        )
        assertEquals(
            expected,
            repositoryDetailsViewModel.repositoryDetails.getOrAwaitValue()::class.java
        )
    }
}