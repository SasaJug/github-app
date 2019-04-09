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

    fun getStargazers(username: String, repositoryName: String, page : Int): Call<List<UserDto>> {
        return service.getStargazersForRepository(username, repositoryName, page)
    }

    fun getContributors(username: String, repositoryName: String, page: Int): Call<List<ContributorDto>> {
        return service.getContributorsForRepository(username,repositoryName, page)
    }

    @Deprecated("use methods with Call return type")
    fun getStargazersForRepository(username: String, repositoryName: String, page : Int): Single<List<UserDto>> {
        return service.getUserRepositoryStargazers(username, repositoryName, page)
    }

    @Deprecated("use methods with Call return type")
    fun getContributorsForRepository(username: String, repositoryName: String, page: Int): Single<List<ContributorDto>> {
        return service.getUserRepositoryContributors(username,repositoryName, page)
    }

}