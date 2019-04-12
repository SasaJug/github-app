package com.sasaj.data

import android.util.Log
import com.sasaj.domain.Repository
import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import com.sasaj.domain.usecases.RequestMoreUseCase
import io.reactivex.Observable

class RepositoryImpl(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) : Repository {

    private var first: String? = ""
    private var prev: String? = ""
    private var next: String? = ""
    private var last: String? = ""

    private var lastRepository: Long = 0
    private var lastStargazersPage: Int = 0
    private var lastContributorsPage: Int = 0

    private var currentUrl: String = ""

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


    override fun getStargazersForRepository(url: String): Observable<List<User>> {

        if (currentUrl != url) {
            currentUrl = url
            first = ""
            prev = ""
            next = url
            last = ""
        }

        if (first == "") {
            localRepository.deleteAllStargazers()
            requestAndSaveStargazers()
        }
        // Get data from the local cache
        return localRepository.getStargazers()
    }

    private fun requestAndSaveStargazers() {
        if (last == null) return
        if (isRequestInProgress) return

        isRequestInProgress = true
        remoteRepository.getStargazers(next!!,
                { stargazers, first, previous, next, last ->
                    lastStargazersPage += 1
                    localRepository.insertStargazers(stargazers)
                    isRequestInProgress = false
                    this.first = first
                    this.prev = previous
                    this.next = next
                    this.last = last
                },
                { error ->
                    Log.e(TAG, error)
                    isRequestInProgress = false
                })
    }

    override fun getContributorsForRepository(url: String): Observable<List<Contributor>> {

        if (currentUrl != url) {
            currentUrl = url
            first = ""
            prev = ""
            next = url
            last = ""
        }

        if (first == "") {
            localRepository.deleteAllContributors()
            requestAndSaveContributors()
        }
        // Get data from the local cache
        return localRepository.getContributors()
    }

    private fun requestAndSaveContributors() {
        if (last == null) return
        if (isRequestInProgress) return

        isRequestInProgress = true
        remoteRepository.getContributors(next!!,
                { contributors, first, previous, next, last ->
                    lastContributorsPage += 1
                    localRepository.insertContributors(contributors)
                    isRequestInProgress = false
                    this.first = first
                    this.prev = previous
                    this.next = next
                    this.last = last
                },
                { error ->
                    Log.e(TAG, error)
                    isRequestInProgress = false
                })
    }


    override fun requestMore(type: Int): Observable<Boolean> {
        return Observable.fromCallable<Boolean> {
            when (type) {
                RequestMoreUseCase.CONST_REPOSITORY -> requestAndSaveRepositories()
                RequestMoreUseCase.CONST_CONTRIBUTOR -> requestAndSaveContributors()
                RequestMoreUseCase.CONST_STARGAZER -> requestAndSaveStargazers()
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