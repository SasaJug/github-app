package com.sasaj.githubapp.di

import com.sasaj.domain.Repository
import com.sasaj.domain.usecases.GetSingleRepositoryUseCase
import com.sasaj.githubapp.common.ASyncTransformer
import com.sasaj.githubapp.detail.DetailVMFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryDetailModule {

    @Provides
    @Singleton
    fun provideGetSingleRepositoriesUseCase(repository: Repository): GetSingleRepositoryUseCase {
        return GetSingleRepositoryUseCase(ASyncTransformer(), repository)
    }

    @Provides
    @Singleton
    fun provideDetailVMFactory(getSingleRepositoryUseCase: GetSingleRepositoryUseCase): DetailVMFactory {
        return DetailVMFactory(getSingleRepositoryUseCase)
    }


}