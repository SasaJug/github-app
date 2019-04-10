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

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun getPublicRepositories(): Observable<List<GithubRepository>> {

        if(lastRepository == 0L){
            localRepository.deleteAllRepositories()
            lastRepository = 0
            requestAndSaveRepositories()
        }


        // Get data from the local cache
        return localRepository.getPublicRepositories()
    }

    override fun requestMore(type: Int, username: String, repositoryName: String) : Observable<Boolean>{
        return Observable.fromCallable<Boolean>{
            when(type){
                RequestMoreUseCase.CONST_REPOSITORY -> requestAndSaveRepositories()
                RequestMoreUseCase.CONST_CONTRIBUTOR -> requestAndSaveContributors(username, repositoryName)
                RequestMoreUseCase.CONST_STARGAZER -> requestAndSaveStargazers(username, repositoryName)
            }
            true
        }
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

    override fun getSingleRepository(username: String, repositoryName: String): Observable<GithubRepository> {
        return remoteRepository.getSingleRepository(username, repositoryName)
    }

    override fun getStargazersForRepository(username: String, repositoryName: String): Observable<List<User>> {
//        return remoteRepository.getStargazersForRepository(username, repositoryName)

        localRepository.deleteAllStargazers()
        lastStargazersPage = 1
        requestAndSaveStargazers(username, repositoryName)

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
//        return remoteRepository.getContributorsForRepository(username, repositoryName)

        localRepository.deleteAllContributors()
        lastContributorsPage = 1
        requestAndSaveContributors(username, repositoryName)

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

    companion object {
        const val TAG: String = "RepositoryImpl"
    }
}