package com.sasaj.githubapp.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sasaj.domain.usecases.GetSingleRepositoryUseCase

class DetailVMFactory(private val getSingleRepositoryUseCase: GetSingleRepositoryUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(getSingleRepositoryUseCase) as T
    }
}