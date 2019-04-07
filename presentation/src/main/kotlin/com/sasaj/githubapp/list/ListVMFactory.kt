package com.sasaj.githubapp.list

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sasaj.domain.usecases.GetAllRepositoriesUseCase

class ListVMFactory(private val getAllRepositoriesUseCase: GetAllRepositoriesUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(getAllRepositoriesUseCase) as T
    }
}