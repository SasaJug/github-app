package com.sasaj.data.httpclient

import com.sasaj.data.httpclient.entities.ContributorDto
import com.sasaj.data.httpclient.entities.RepositoryDto
import com.sasaj.data.httpclient.entities.StargazerDto
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface GitHubService {

    @GET("repositories")
    fun getRepositories(@Query("since") repositoryId: Long): Call<List<RepositoryDto>>

    @GET("repos/{user}/{repositoryName}")
    fun getUserRepository(@Path("user") user: String,
                          @Path("repositoryName") repositoryName: String): Single<RepositoryDto>


    @GET
    fun getStargazersForRepository(@Url url: String): Call<List<StargazerDto>>


    @GET
    fun getContributorsForRepository(@Url url: String): Call<List<ContributorDto>>

}