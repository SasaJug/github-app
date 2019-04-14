package com.sasaj.domain

import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import com.sasaj.domain.entities.State
import io.reactivex.Observable

interface Repository {

    fun getPublicRepositories(): Observable<List<GithubRepository>>

    fun requestMore(type: Int, url: String) : Observable<Boolean>

    fun getSingleRepository(username: String, repositoryName: String): Observable<GithubRepository>

    fun getStargazersForRepository(url: String): Triple<Observable<List<User>>, Observable<Any>, Observable<State>>

    fun getContributorsForRepository(url: String): Triple<Observable<List<User>>, Observable<Any>, Observable<State>>
}