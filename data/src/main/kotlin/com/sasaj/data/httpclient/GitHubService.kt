package com.sasaj.data.httpclient

import com.sasaj.data.entities.ContributorDto
import com.sasaj.data.entities.RepositoryDto
import com.sasaj.data.entities.UserDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GitHubService {

    @GET("repositories")
    fun getRepositories(@Query("since") cityId: Int): Single<List<RepositoryDto>>

    @GET("repos/{user}/{repositoryName}")
    fun getUserRepository(@Path("user") user: String,
                          @Path("repositoryName") repositoryName: String): Single<RepositoryDto>


    @GET("repos/{user}/{repositoryName}/contributors")
    fun getUserRepositoryContributors(@Path("user") user: String,
                          @Path("repositoryName") repositoryName: String): Single<List<ContributorDto>>


    @GET("repos/{user}/{repositoryName}/contributors")
    fun getUserRepositoryStargazers(@Path("user") user: String,
                                      @Path("repositoryName") repositoryName: String): Single<List<UserDto>>
}