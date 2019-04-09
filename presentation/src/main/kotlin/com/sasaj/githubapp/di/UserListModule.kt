package com.sasaj.githubapp.di

import com.sasaj.domain.Repository
import com.sasaj.domain.usecases.GetRepositoryContributorsUseCase
import com.sasaj.domain.usecases.GetRepositoryStargazersUseCase
import com.sasaj.domain.usecases.RequestMoreUseCase
import com.sasaj.githubapp.common.ASyncTransformer
import com.sasaj.githubapp.userlist.UserListVMFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserListModule {

    @Provides
    @Singleton
    fun provideGetRepositoryStargazersUseCase(repository: Repository): GetRepositoryStargazersUseCase {
        return GetRepositoryStargazersUseCase(ASyncTransformer(), repository)
    }

    @Provides
    @Singleton
    fun provideGetRepositoryContributorsUseCase(repository: Repository): GetRepositoryContributorsUseCase {
        return GetRepositoryContributorsUseCase(ASyncTransformer(), repository)
    }

    @Provides
    @Singleton
    fun provideUserListVMFactory(getRepositoryStargazersUseCase: GetRepositoryStargazersUseCase,
                                 getRepositoryContributorsUseCase: GetRepositoryContributorsUseCase,
                                 requestMoreUseCase: RequestMoreUseCase): UserListVMFactory {
        return UserListVMFactory(getRepositoryStargazersUseCase, getRepositoryContributorsUseCase, requestMoreUseCase)
    }
}