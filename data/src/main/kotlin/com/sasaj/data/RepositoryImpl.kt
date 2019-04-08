package com.sasaj.data

import android.util.Log
import com.sasaj.domain.Repository
import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import io.reactivex.Observable

class RepositoryImpl(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) : Repository {

    private var lastRepository : Long = 0
    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun getPublicRepositories(): Observable<List<GithubRepository>> {

        localRepository.deleteAllRepositories()
        lastRepository = 0
        requestAndSaveData()

        // Get data from the local cache
        return localRepository.getPublicRepositories()
    }

    override fun requestMore() {
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        remoteRepository.getPublicRepositories(lastRepository,
                { repos ->
                    lastRepository = repos.last().id
                    localRepository.insertRepositories(repos)
                    isRequestInProgress = false
                },
                { error ->
                    Log.e(TAG, error)
                    isRequestInProgress = false
                })

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

    companion object {
        const val TAG: String = "RepositoryImpl"
    }
}