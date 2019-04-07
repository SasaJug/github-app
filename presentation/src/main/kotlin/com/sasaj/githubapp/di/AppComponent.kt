package com.sasaj.githubapp.di

import com.sasaj.githubapp.list.RepositoryListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (ApplicationModule::class)
])
interface AppComponent {
    fun inject(repositoryListActivity : RepositoryListActivity)
}