package com.example.githubrepos

import com.example.githubrepos.di.DEFAULT
import com.example.githubrepos.di.IO
import com.example.githubrepos.repository.GithubRepoRepository
import com.example.githubrepos.ui.repository.RepositoryViewModel
import com.example.githubrepos.ui.repository.repositorydetails.RepositoryDetailsViewModel
import io.mockk.mockkClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import org.koin.core.qualifier.named
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val module = module {

    //mock repository
    single { mockkClass(GithubRepoRepository::class, relaxed = true) }


    //test coroutine dispatcher
    single(named(IO)) { CoroutineScope(TestCoroutineScope().coroutineContext) }
    single(named(DEFAULT)) { CoroutineScope(TestCoroutineScope().coroutineContext) }

    // viewModel
    single { RepositoryDetailsViewModel(get()) }
    single { RepositoryViewModel(get()) }

}