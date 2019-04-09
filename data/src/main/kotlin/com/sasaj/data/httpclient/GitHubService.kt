package com.sasaj.data.httpclient

import com.sasaj.data.httpclient.entities.ContributorDto
import com.sasaj.data.httpclient.entities.RepositoryDto
import com.sasaj.data.httpclient.entities.UserDto
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GitHubService {

    @GET("repositories")
    fun getRepositories(@Query("since") repositoryId: Long): Call<List<RepositoryDto>>

    @GET("repos/{user}/{repositoryName}")
    fun getUserRepository(@Path("user") user: String,
                          @Path("repositoryName") repositoryName: String): Single<RepositoryDto>



    @GET("repos/{user}/{repositoryName}/stargazers")
    fun getStargazersForRepository(@Path("user") user: String,
                                    @Path("repositoryName") repositoryName: String,
                                    @Query("page") page: Int): Call<List<UserDto>>


    @GET("repos/{user}/{repositoryName}/contributors")
    fun getContributorsForRepository(@Path("user") user: String,
                                      @Path("repositoryName") repositoryName: String,
                                      @Query("page") page: Int): Call<List<ContributorDto>>


    @GET("repos/{user}/{repositoryName}/contributors")
    fun getUserRepositoryContributors(@Path("user") user: String,
                                      @Path("repositoryName") repositoryName: String,
                                      @Query("page") page: Int): Single<List<ContributorDto>>


    @GET("repos/{user}/{repositoryName}/stargazers")
    fun getUserRepositoryStargazers(@Path("user") user: String,
                                    @Path("repositoryName") repositoryName: String,
                                    @Query("page") page: Int): Single<List<UserDto>>
}