package com.sasaj.data

import com.sasaj.data.common.*
import com.sasaj.data.database.AppDb
import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import io.reactivex.Observable
import java.util.concurrent.Executors

class LocalRepository(private val appDb: AppDb,
                      private val repositoryDbToDomainMapper: RepositoryDbToDomainMapper,
                      private val repositoryDomainToDbMapper: RepositoryDomainToDbMapper,
                      private val contributorDbToDomainMapper: ContributorDbToDomainMapper,
                      private val contributorDomainToDbMapper: ContributorDomainToDbMapper,
                      private val stargazerDbToDomainMapper: StargazerDbToDomainMapper,
                      private val stargazerDomainToDbMapper: StargazerDomainToDbMapper) {


    fun getPublicRepositories(): Observable<List<GithubRepository>> {
        return appDb.gitHubRepositoryDao().getRepositories().map { repoDbList -> repositoryDbToDomainMapper.mapFrom(repoDbList) }.toObservable()
    }

    fun insertRepositories(list: List<GithubRepository>) {
        Executors.newSingleThreadExecutor().execute {
            val listDb = list.map { repo -> repositoryDomainToDbMapper.mapFrom(repo) }
            appDb.gitHubRepositoryDao().insert(listDb)
        }
    }

    fun deleteAllRepositories() {
        Executors.newSingleThreadExecutor().execute {
            appDb.gitHubRepositoryDao().deleteAll()
        }
    }

    fun getContributors(): Observable<List<Contributor>> {
        return appDb.contributorDao().getContributors().map { contributorDbList -> contributorDbToDomainMapper.mapFrom(contributorDbList) }.toObservable()
    }

    fun insertContributors(list: List<Contributor>) {
        Executors.newSingleThreadExecutor().execute {
            val listDb = list.map { contributor -> contributorDomainToDbMapper.mapFrom(contributor) }
            appDb.contributorDao().insert(listDb)
        }
    }

    fun deleteAllContributors() {
        Executors.newSingleThreadExecutor().execute {
            appDb.contributorDao().deleteAll()
        }
    }


    fun getStargazers(): Observable<List<User>> {
        return appDb.stargazerDao().getStargazers().map { stargazerDbList -> stargazerDbToDomainMapper.mapFrom(stargazerDbList) }.toObservable()
    }

    fun insertStargazers(list: List<User>) {
        Executors.newSingleThreadExecutor().execute {
            val listDb = list.map { stargazer -> stargazerDomainToDbMapper.mapFrom(stargazer) }
            appDb.stargazerDao().insert(listDb)
        }
    }

    fun deleteAllStargazers() {
        Executors.newSingleThreadExecutor().execute {
            appDb.stargazerDao().deleteAll()
        }
    }
}