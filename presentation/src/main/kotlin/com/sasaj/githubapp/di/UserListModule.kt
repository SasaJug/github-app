package com.sasaj.githubapp.di

import com.sasaj.domain.Repository
import com.sasaj.domain.usecases.GetRepositoryContributorsUseCase
import com.sasaj.domain.usecases.GetRepositoryStargazersUseCase
import com.sasaj.domain.usecases.GetRepositoryUsersUseCase
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
    fun provideGetRepositoryUsersUseCase(repository: Repository): GetRepositoryUsersUseCase {
        return GetRepositoryUsersUseCase(ASyncTransformer(), repository)
    }

    @Provides
    @Singleton
    fun provideUserListVMFactory(getRepositoryUsersUseCase: GetRepositoryUsersUseCase,
                                 requestMoreUseCase: RequestMoreUseCase): UserListVMFactory {
        return UserListVMFactory(getRepositoryUsersUseCase, requestMoreUseCase)
    }
}