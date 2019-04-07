package com.sasaj.data.httpclient

import com.sasaj.data.entities.ContributorDto
import com.sasaj.data.entities.RepositoryDto
import com.sasaj.data.entities.UserDto
import io.reactivex.Single

open class RetrofitClient (private val service : GitHubService){

    fun getRepositories(since: Int): Single<List<RepositoryDto>> {
        return service.getRepositories(since)
    }

    fun getRepository(username: String, repositoryName: String): Single<RepositoryDto> {
        return service.getUserRepository(username, repositoryName)
    }

    fun getStargazersForRepository(username: String, repositoryName: String): Single<List<UserDto>> {
        return service.getUserRepositoryStargazers(username, repositoryName)
    }

    fun getContributorsForRepository(username: String, repositoryName: String): Single<List<ContributorDto>> {
        return service.getUserRepositoryContributors(username,repositoryName)
    }

}