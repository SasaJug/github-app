package com.sasaj.data

import com.sasaj.data.common.RepositoryDbToDomainMapper
import com.sasaj.data.common.RepositoryDomainToDbMapper
import com.sasaj.data.database.AppDb
import com.sasaj.domain.entities.GithubRepository
import io.reactivex.Observable
import java.util.concurrent.Executors

class LocalRepository(private val appDb: AppDb,
                      private val repositoryDbToDomainMapper: RepositoryDbToDomainMapper,
                      private val repositoryDomainToDbMapper: RepositoryDomainToDbMapper) {


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
}