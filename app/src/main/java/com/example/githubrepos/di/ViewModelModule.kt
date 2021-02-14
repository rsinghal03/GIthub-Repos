package com.example.githubrepos.di

import com.example.githubrepos.ui.repository.RepositoryViewModel
import com.example.githubrepos.ui.repository.repositorydetails.RepositoryDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { RepositoryViewModel(get()) }
    viewModel { RepositoryDetailsViewModel(get()) }
}