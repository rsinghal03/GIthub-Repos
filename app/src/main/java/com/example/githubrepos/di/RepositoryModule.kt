package com.example.githubrepos.di

import com.example.githubrepos.repository.GithubRepoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { GithubRepoRepository(get()) }
}