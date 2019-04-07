package com.sasaj.data

import com.sasaj.domain.Repository
import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import io.reactivex.Observable

class RepositoryImpl(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) : Repository {

    override fun getPublicRepositories(): Observable<List<GithubRepository>> {
        return remoteRepository.getPublicRepositories()
    }

    override fun getSingleRepository(username: String, repositoryName: String): Observable<GithubRepository> {
        return remoteRepository.getSingleRepository(username, repositoryName)
    }

    override fun getStargazersForRepository(username: String, repositoryName: String): Observable<List<User>> {
        return remoteRepository.getStargazersForRepository(username, repositoryName)
    }

    override fun getContributorsForRepository(username: String, repositoryName: String): Observable<List<Contributor>> {
        return remoteRepository.getContributorsForRepository(username, repositoryName)
    }
}