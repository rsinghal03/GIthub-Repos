package com.example.githubrepos.di

import com.example.githubrepos.networking.GithubApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
// all dependency related to network module
val networkModule = module {
    single { getGithubApiService() }
}

private fun getGithubApiService(): GithubApiService {
    return createRetrofit().create(GithubApiService::class.java)
}

private fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}

private fun getOkHttpClient(): OkHttpClient {

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .readTimeout(READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()
}

private const val BASE_URL = "https://api.github.com/"
private const val READ_WRITE_TIMEOUT: Long = 60
private const val CONNECTION_TIMEOUT: Long = 60