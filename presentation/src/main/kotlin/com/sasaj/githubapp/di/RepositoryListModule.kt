package com.sasaj.githubapp.di

import com.sasaj.domain.Repository
import com.sasaj.domain.usecases.GetAllRepositoriesUseCase
import com.sasaj.domain.usecases.RequestMoreUseCase
import com.sasaj.githubapp.common.ASyncTransformer
import com.sasaj.githubapp.list.ListVMFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryListModule {

    @Provides
    @Singleton
    fun provideGetAllRepositoriesUseCase(repository: Repository): GetAllRepositoriesUseCase {
        return GetAllRepositoriesUseCase(ASyncTransformer(), repository)
    }


    @Provides
    @Singleton
    fun provideListVMFactory(getAllRepositoriesUseCase: GetAllRepositoriesUseCase, requestMoreUseCase: RequestMoreUseCase): ListVMFactory {
        return ListVMFactory(getAllRepositoriesUseCase, requestMoreUseCase)
    }
}