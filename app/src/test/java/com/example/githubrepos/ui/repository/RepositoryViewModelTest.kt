package com.example.githubrepos.ui.repository

import androidx.paging.PagingData
import com.example.githubrepos.BaseTest
import com.example.githubrepos.model.remote.repositoryresponse.Item
import com.example.githubrepos.repository.GithubRepoRepository
import com.example.util.getOrAwaitValue
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.test.inject

@ExperimentalCoroutinesApi
class RepositoryViewModelTest : BaseTest() {

    private val repositoryViewModel: RepositoryViewModel by inject()
    private val githubRepoRepository: GithubRepoRepository by inject()

    @Test
    fun subscribeToPagingData() {
        val expected = PagingData.from(listOf<Item>())
        TestCoroutineScope().launch {
            val flow =
                MutableSharedFlow<PagingData<Item>>(replay = 1).apply { emit(expected) }
            every { githubRepoRepository.flow } returns flow
        }
        assertEquals(expected, repositoryViewModel.subscribeToPagingData().getOrAwaitValue())
    }

    @Test
    fun onSearchClick() {
        val query =
            "someString" // do not use anyString() as it return "" blank string, expected not-blank string
        coEvery { githubRepoRepository.getRepos(query, 20, any()) } just runs
        repositoryViewModel.onSearchClick().invoke(query)
        coVerify { githubRepoRepository.getRepos(query, 20, any()) }
    }

}