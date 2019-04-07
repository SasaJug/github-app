package com.sasaj.githubapp.userlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sasaj.domain.usecases.GetRepositoryContributorsUseCase
import com.sasaj.domain.usecases.GetRepositoryStargazersUseCase

class UserListVMFactory(private val getRepositoryStargazersUseCase: GetRepositoryStargazersUseCase,
                        private val getRepositoryContributorsUseCase: GetRepositoryContributorsUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserListViewModel(getRepositoryStargazersUseCase, getRepositoryContributorsUseCase) as T
    }
}