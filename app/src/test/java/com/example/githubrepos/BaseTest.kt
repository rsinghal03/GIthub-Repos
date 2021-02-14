package com.example.githubrepos

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest

@ExperimentalCoroutinesApi
open class BaseTest : AutoCloseKoinTest() {

    private val mockContext = mockkClass(Context::class)
    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    init {
        startKoin {
            androidContext(mockContext)
            modules(module)
        }
    }

    @Before
    open fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    open fun tearDown() {
        Dispatchers.resetMain()
    }
}