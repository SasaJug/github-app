package com.sasaj.domain

import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import com.sasaj.domain.entities.State
import io.reactivex.Observable

interface Repository {

    fun getPublicRepositories(): Observable<List<GithubRepository>>

    fun requestMore(type: Int) : Observable<Boolean>

    fun getSingleRepository(username: String, repositoryName: String): Observable<GithubRepository>

    fun getStargazersForRepository(url: String): Pair<Observable<List<User>>, Observable<Any>>

    fun getContributorsForRepository(url: String): Pair<Observable<List<User>>, Observable<Any>>
}