package com.sasaj.data

import com.sasaj.domain.Repository
import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import io.reactivex.Observable

class LocalRepository : Repository {
    override fun getPublicRepositories(): Observable<List<GithubRepository>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSingleRepository(username: String, repositoryName: String): Observable<GithubRepository> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStargazersForRepository(username: String, repositoryName: String): Observable<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContributorsForRepository(username: String, repositoryName: String): Observable<List<Contributor>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}