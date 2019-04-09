package com.sasaj.githubapp

import android.app.Application
import android.util.Log
import com.sasaj.githubapp.di.*
import io.reactivex.plugins.RxJavaPlugins

class GitHubApplication :Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { e -> Log.e("App", e.message, e) }
        appComponent = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .repositoryListModule(RepositoryListModule())
                .repositoryDetailModule(RepositoryDetailModule())
                .userListModule(UserListModule())
                .build()
    }
}