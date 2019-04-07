package com.sasaj.githubapp.di

import android.content.Context
import android.net.ConnectivityManager
import com.sasaj.data.LocalRepository
import com.sasaj.data.RemoteRepository
import com.sasaj.data.RepositoryImpl
import com.sasaj.data.common.ContributorDtoToDomainMapper
import com.sasaj.data.common.RepositoryDtoToDomainMapper
import com.sasaj.data.common.UserDtoToDomainMapper
import com.sasaj.data.httpclient.GitHubService
import com.sasaj.data.httpclient.RetrofitClient
import com.sasaj.domain.NetworkManager
import com.sasaj.domain.Repository
import com.sasaj.domain.usecases.GetAllRepositoriesUseCase
import com.sasaj.domain.usecases.GetRepositoryContributorsUseCase
import com.sasaj.domain.usecases.GetRepositoryStargazersUseCase
import com.sasaj.domain.usecases.GetSingleRepositoryUseCase
import com.sasaj.githubapp.BuildConfig
import com.sasaj.githubapp.common.ASyncTransformer
import com.sasaj.githubapp.common.NetworkManagerImpl
import com.sasaj.githubapp.list.ListVMFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.Gson



@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideNetworkManager(connectivityManager: ConnectivityManager): NetworkManager {
        return NetworkManagerImpl(connectivityManager)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return  OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return  Retrofit.Builder().baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideGitHubService(retrofit: Retrofit) : GitHubService {
        return  retrofit.create(GitHubService::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(githubService : GitHubService): RetrofitClient {
        return  RetrofitClient(githubService)
    }


    @Provides
    @Singleton
    fun provideRepositoryDtoToDomainMapper(): RepositoryDtoToDomainMapper {
        return  RepositoryDtoToDomainMapper()
    }

    @Provides
    @Singleton
    fun provideUserDtoToDomainMapper(): UserDtoToDomainMapper {
        return  UserDtoToDomainMapper()
    }

    @Provides
    @Singleton
    fun provideContributorDtoToToDomainMapper(): ContributorDtoToDomainMapper {
        return ContributorDtoToDomainMapper()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(): LocalRepository {
        return  LocalRepository()
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(httpClient : RetrofitClient,
                                repositoryDtoToDomainMapper: RepositoryDtoToDomainMapper,
                                userDtoToDomainMapper: UserDtoToDomainMapper,
                                contributorDtoToDomainMapper: ContributorDtoToDomainMapper): RemoteRepository {
        return  RemoteRepository(httpClient, repositoryDtoToDomainMapper, userDtoToDomainMapper, contributorDtoToDomainMapper)
    }

    @Provides
    @Singleton
    fun provideRepository(localRepository: LocalRepository, remoteRepository: RemoteRepository): Repository {
        return RepositoryImpl(remoteRepository, localRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllRepositoriesUseCase(repository: Repository): GetAllRepositoriesUseCase{
        return GetAllRepositoriesUseCase(ASyncTransformer(), repository)
    }

    @Provides
    @Singleton
    fun provideGetSingleRepositoriesUseCase(repository: Repository): GetSingleRepositoryUseCase{
        return GetSingleRepositoryUseCase(ASyncTransformer(), repository)
    }

    @Provides
    @Singleton
    fun provideGetRepositoryStargazersUseCase(repository: Repository): GetRepositoryStargazersUseCase{
        return GetRepositoryStargazersUseCase(ASyncTransformer(), repository)
    }

    @Provides
    @Singleton
    fun provideGetRepositoryContributorsUseCase(repository: Repository): GetRepositoryContributorsUseCase{
        return GetRepositoryContributorsUseCase(ASyncTransformer(), repository)
    }

    @Provides
    @Singleton
    fun provideListVMFactory(getAllRepositoriesUseCase: GetAllRepositoriesUseCase): ListVMFactory{
        return ListVMFactory(getAllRepositoriesUseCase)
    }
}