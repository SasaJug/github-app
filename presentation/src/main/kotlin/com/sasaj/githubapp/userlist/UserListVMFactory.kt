package com.sasaj.githubapp.userlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sasaj.domain.usecases.GetRepositoryContributorsUseCase
import com.sasaj.domain.usecases.GetRepositoryStargazersUseCase
import com.sasaj.domain.usecases.RequestMoreUseCase

class UserListVMFactory(private val getRepositoryStargazersUseCase: GetRepositoryStargazersUseCase,
                        private val getRepositoryContributorsUseCase: GetRepositoryContributorsUseCase,
                        private val requestMoreUseCase: RequestMoreUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserListViewModel(getRepositoryStargazersUseCase, getRepositoryContributorsUseCase, requestMoreUseCase) as T
    }
}