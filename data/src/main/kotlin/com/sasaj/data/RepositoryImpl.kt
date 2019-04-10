package com.sasaj.data

import android.util.Log
import com.sasaj.domain.Repository
import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import com.sasaj.domain.usecases.RequestMoreUseCase
import io.reactivex.Observable

class RepositoryImpl(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) : Repository {

    private var lastRepository: Long = 0
    private var lastStargazersPage: Int = 0
    private var lastContributorsPage: Int = 0

    private var currentUserName: String = ""
    private var currentRepoName: String = ""

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun getPublicRepositories(): Observable<List<GithubRepository>> {

        if (lastRepository == 0L) {
            localRepository.deleteAllRepositories()
            requestAndSaveRepositories()
        }

        // Get data from the local cache
        return localRepository.getPublicRepositories()
    }


    private fun requestAndSaveRepositories() {
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


    override fun getStargazersForRepository(username: String, repositoryName: String): Observable<List<User>> {

        if (currentUserName != username || currentRepoName != repositoryName) {
            lastStargazersPage = 1
            currentUserName = username
            currentRepoName = repositoryName
        }

        if (lastStargazersPage == 1) {
            localRepository.deleteAllStargazers()
            requestAndSaveStargazers(username, repositoryName)
        }
        // Get data from the local cache
        return localRepository.getStargazers()
    }

    private fun requestAndSaveStargazers(username: String, repositoryName: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        remoteRepository.getStargazers(lastStargazersPage, username, repositoryName,
                { stargazers ->
                    lastStargazersPage += 1
                    localRepository.insertStargazers(stargazers)
                    isRequestInProgress = false
                },
                { error ->
                    Log.e(TAG, error)
                    isRequestInProgress = false
                })
    }

    override fun getContributorsForRepository(username: String, repositoryName: String): Observable<List<Contributor>> {

        if (currentUserName != username || currentRepoName != repositoryName) {
            lastContributorsPage = 1
            currentUserName = username
            currentRepoName = repositoryName
        }

        if (lastContributorsPage == 1) {
            localRepository.deleteAllContributors()
            requestAndSaveContributors(username, repositoryName)
        }
        // Get data from the local cache
        return localRepository.getContributors()
    }

    private fun requestAndSaveContributors(username: String, repositoryName: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        remoteRepository.getContributors(lastContributorsPage, username, repositoryName,
                { contributors ->
                    lastContributorsPage += 1
                    localRepository.insertContributors(contributors)
                    isRequestInProgress = false
                },
                { error ->
                    Log.e(TAG, error)
                    isRequestInProgress = false
                })
    }


    override fun requestMore(type: Int, username: String, repositoryName: String): Observable<Boolean> {
        return Observable.fromCallable<Boolean> {
            when (type) {
                RequestMoreUseCase.CONST_REPOSITORY -> requestAndSaveRepositories()
                RequestMoreUseCase.CONST_CONTRIBUTOR -> requestAndSaveContributors(username, repositoryName)
                RequestMoreUseCase.CONST_STARGAZER -> requestAndSaveStargazers(username, repositoryName)
            }
            true
        }
    }


    override fun getSingleRepository(username: String, repositoryName: String): Observable<GithubRepository> {
        return remoteRepository.getSingleRepository(username, repositoryName)
    }

    companion object {
        const val TAG: String = "RepositoryImpl"
    }
}