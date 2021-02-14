package com.example.githubrepos

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.example.githubrepos.di.applicationModule
import com.example.githubrepos.ui.AppBindingAdapter
import com.example.githubrepos.ui.AppBindingComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class GithubReposApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GithubReposApplication)
            modules(modules = applicationModule)
        }

        Timber.plant(Timber.DebugTree())

        DataBindingUtil.setDefaultComponent(object : AppBindingComponent {
            override fun getAppBindingAdapter(): AppBindingAdapter {
                return AppBindingAdapter()
            }
        })
    }
}