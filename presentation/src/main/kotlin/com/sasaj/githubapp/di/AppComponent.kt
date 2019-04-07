package com.sasaj.githubapp.di

import com.sasaj.githubapp.detail.RepositoryDetailFragment
import com.sasaj.githubapp.list.RepositoryListActivity
import com.sasaj.githubapp.userlist.UserListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (ApplicationModule::class)
])
interface AppComponent {
    fun inject(repositoryListActivity: RepositoryListActivity)
    fun inject(repositoryDetailFragment: RepositoryDetailFragment)
    fun inject(userListActivity: UserListActivity)
}