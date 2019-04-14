package com.sasaj.githubapp.userlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sasaj.domain.usecases.GetRepositoryContributorsUseCase
import com.sasaj.domain.usecases.GetRepositoryStargazersUseCase
import com.sasaj.domain.usecases.GetRepositoryUsersUseCase
import com.sasaj.domain.usecases.RequestMoreUseCase

class UserListVMFactory(private val getRepositoryUsersUseCase: GetRepositoryUsersUseCase,
                        private val requestMoreUseCase: RequestMoreUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserListViewModel(getRepositoryUsersUseCase, requestMoreUseCase) as T
    }
}