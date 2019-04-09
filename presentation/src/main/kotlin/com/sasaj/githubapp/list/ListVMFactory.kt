package com.sasaj.githubapp.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sasaj.domain.usecases.GetAllRepositoriesUseCase
import com.sasaj.domain.usecases.RequestMoreUseCase

class ListVMFactory(private val getAllRepositoriesUseCase: GetAllRepositoriesUseCase,
                    private val requestMoreUseCase: RequestMoreUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(getAllRepositoriesUseCase, requestMoreUseCase) as T
    }
}