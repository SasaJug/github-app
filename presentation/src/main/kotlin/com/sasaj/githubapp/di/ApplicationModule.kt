package com.sasaj.githubapp.di

import android.arch.persistence.room.Room
import android.content.Context
import android.net.ConnectivityManager
import com.sasaj.data.LocalRepository
import com.sasaj.data.RemoteRepository
import com.sasaj.data.RepositoryImpl
import com.sasaj.data.database.mappers.*
import com.sasaj.data.database.AppDb
import com.sasaj.data.httpclient.GitHubService
import com.sasaj.data.httpclient.RetrofitClient
import com.sasaj.data.httpclient.mappers.ContributorDtoToDomainMapper
import com.sasaj.data.httpclient.mappers.RepositoryDtoToDomainMapper
import com.sasaj.data.httpclient.mappers.UserDtoToDomainMapper
import com.sasaj.domain.NetworkManager
import com.sasaj.domain.Repository
import com.sasaj.domain.usecases.*
import com.sasaj.githubapp.BuildConfig
import com.sasaj.githubapp.common.ASyncTransformer
import com.sasaj.githubapp.common.NetworkManagerImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


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
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideGitHubService(retrofit: Retrofit): GitHubService {
        return retrofit.create(GitHubService::class.java)
    }

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): AppDb {
        return Room.databaseBuilder(context, AppDb::class.java, "GithubAppDb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(githubService: GitHubService): RetrofitClient {
        return RetrofitClient(githubService)
    }

    @Provides
    @Singleton
    fun provideRepositoryDtoToDomainMapper(): RepositoryDtoToDomainMapper {
        return RepositoryDtoToDomainMapper()
    }

    @Provides
    @Singleton
    fun provideUserDtoToDomainMapper(): UserDtoToDomainMapper {
        return UserDtoToDomainMapper()
    }

    @Provides
    @Singleton
    fun provideContributorDtoToToDomainMapper(): ContributorDtoToDomainMapper {
        return ContributorDtoToDomainMapper()
    }


    @Provides
    @Singleton
    fun providerepositoryDbToToDomainMapper(): RepositoryDbToDomainMapper {
        return RepositoryDbToDomainMapper()
    }

    @Provides
    @Singleton
    fun provideRepositoryDomainToDbMapper(): RepositoryDomainToDbMapper {
        return RepositoryDomainToDbMapper()
    }

    @Provides
    @Singleton
    fun provideContributorDomainToDbMapper(): ContributorDomainToDbMapper {
        return ContributorDomainToDbMapper()
    }

    @Provides
    @Singleton
    fun provideStargazerDbToDomainMapper(): StargazerDbToDomainMapper {
        return StargazerDbToDomainMapper()
    }

    @Provides
    @Singleton
    fun provideStargazerDomainToDbMapper(): StargazerDomainToDbMapper {
        return StargazerDomainToDbMapper()
    }

    @Provides
    @Singleton
    fun provideContributorDbToDomainMapper(): ContributorDbToDomainMapper {
        return ContributorDbToDomainMapper()
    }


    @Provides
    @Singleton
    fun provideStateDomainToDbMapper(): StateDomainToDbMapper {
        return StateDomainToDbMapper()
    }

    @Provides
    @Singleton
    fun provideStateDbToDomainMapper(): StateDbToDomainMapper {
        return StateDbToDomainMapper()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(appDb: AppDb,
                               repositoryDbToDomainMapper: RepositoryDbToDomainMapper,
                               repositoryDomainToDbMapper: RepositoryDomainToDbMapper,
                               contributorDbToDomainMapper: ContributorDbToDomainMapper,
                               contributorDomainToDbMapper: ContributorDomainToDbMapper,
                               stargazerDbToDomainMapper: StargazerDbToDomainMapper,
                               stargazerDomainToDbMapper: StargazerDomainToDbMapper,
                               stateDbToDomainMapper: StateDbToDomainMapper,
                               stateDomainToDbMapper: StateDomainToDbMapper): LocalRepository {
        return LocalRepository(appDb,
                repositoryDbToDomainMapper,
                repositoryDomainToDbMapper,
                contributorDbToDomainMapper,
                contributorDomainToDbMapper,
                stargazerDbToDomainMapper,
                stargazerDomainToDbMapper,
                stateDbToDomainMapper,
                stateDomainToDbMapper)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(httpClient: RetrofitClient,
                                repositoryDtoToDomainMapper: RepositoryDtoToDomainMapper,
                                userDtoToDomainMapper: UserDtoToDomainMapper,
                                contributorDtoToDomainMapper: ContributorDtoToDomainMapper): RemoteRepository {
        return RemoteRepository(httpClient, repositoryDtoToDomainMapper, userDtoToDomainMapper, contributorDtoToDomainMapper)
    }

    @Provides
    @Singleton
    fun provideRepository(localRepository: LocalRepository, remoteRepository: RemoteRepository): Repository {
        return RepositoryImpl(remoteRepository, localRepository)
    }

    @Provides
    @Singleton
    fun provideReqestMoreUseCase(repository: Repository): RequestMoreUseCase {
        return RequestMoreUseCase(ASyncTransformer(), repository)
    }

}