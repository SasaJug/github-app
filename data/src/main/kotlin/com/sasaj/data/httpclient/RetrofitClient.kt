package com.sasaj.data.httpclient

import com.sasaj.data.httpclient.entities.ContributorDto
import com.sasaj.data.httpclient.entities.RepositoryDto
import com.sasaj.data.httpclient.entities.UserDto
import io.reactivex.Single
import retrofit2.Call

open class RetrofitClient (private val service : GitHubService){

    fun getRepositories(since: Long): Call<List<RepositoryDto>> {
        return service.getRepositories(since)
    }

    fun getRepository(username: String, repositoryName: String): Single<RepositoryDto> {
        return service.getUserRepository(username, repositoryName)
    }

    fun getStargazers(url: String): Call<List<UserDto>> {
        return service.getStargazersForRepository(url)
    }

    fun getContributors(url: String): Call<List<ContributorDto>> {
        return service.getContributorsForRepository(url)
    }

}