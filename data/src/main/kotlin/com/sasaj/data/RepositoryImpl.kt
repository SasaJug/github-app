package com.sasaj.data

import android.util.Log
import com.sasaj.domain.Repository
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.State
import com.sasaj.domain.entities.User
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RepositoryImpl(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) : Repository {


    private val errorObservable: PublishSubject<Any> = PublishSubject.create()

    private var lastRepository: Long = 0

    private var currentStargazerUrl: String? = null
    private var currentContributorUrl: String? = null

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


    override fun getStargazersForRepository(url: String): Triple<Observable<List<User>>, Observable<Any>, Observable<State>> {

        if (currentStargazerUrl == null || currentStargazerUrl != url) {
            currentStargazerUrl = url
            localRepository.deleteAllStargazers()
            localRepository.deleteState(State.CONST_STARGAZER)
            requestAndSaveStargazers(url)
        }
        // Get data from the local cache
        return Triple(localRepository.getStargazers(), errorObservable, localRepository.getState(State.CONST_STARGAZER))
    }

    private fun requestAndSaveStargazers(url: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        remoteRepository.getStargazers(url,
                { stargazers, state ->
                    localRepository.insertStargazers(stargazers)
                    state.id = State.CONST_STARGAZER
                    localRepository.insertState(state)
                    isRequestInProgress = false
                },
                { error ->
                    Log.e(TAG, error.message)
                    errorObservable.onError(error)
                    isRequestInProgress = false
                })
    }

    override fun getContributorsForRepository(url: String): Triple<Observable<List<User>>, Observable<Any>, Observable<State>> {

        if (currentContributorUrl == null || currentContributorUrl != url) {
            currentContributorUrl = url
            localRepository.deleteAllContributors()
            localRepository.deleteState(State.CONST_CONTRIBUTOR)
            requestAndSaveContributors(url)
        }
        // Get data from the local cache
        return Triple(localRepository.getContributors(), errorObservable, localRepository.getState(State.CONST_CONTRIBUTOR))
    }

    private fun requestAndSaveContributors(url: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        remoteRepository.getContributors(url,
                { contributors, state ->
                    localRepository.insertContributors(contributors)
                    state.id = State.CONST_CONTRIBUTOR
                    localRepository.insertState(state)
                    isRequestInProgress = false
                },
                { error ->
                    Log.e(TAG, error.message)
                    errorObservable.onNext(error)
                    isRequestInProgress = false
                })
    }


    override fun requestMore(type: Int, url: String): Observable<Boolean> {
        return Observable.fromCallable<Boolean> {
            when (type) {
                State.CONST_REPOSITORY -> requestAndSaveRepositories()
                State.CONST_CONTRIBUTOR -> requestAndSaveContributors(url)
                State.CONST_STARGAZER -> requestAndSaveStargazers(url)
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