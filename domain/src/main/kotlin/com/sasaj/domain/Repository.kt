package com.sasaj.domain

import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import io.reactivex.Observable

interface Repository {

    fun getPublicRepositories(): Observable<List<GithubRepository>>

    fun requestMore(type: Int, username: String, repositoryName: String) : Observable<Boolean>

    fun getSingleRepository(username: String, repositoryName: String): Observable<GithubRepository>

    fun getStargazersForRepository(username: String, repositoryName: String): Observable<List<User>>

    fun getContributorsForRepository(username: String, repositoryName: String): Observable<List<Contributor>>
}