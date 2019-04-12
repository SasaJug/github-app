package com.sasaj.data

import android.util.Log
import com.sasaj.data.common.ContributorDtoToDomainMapper
import com.sasaj.data.common.RepositoryDtoToDomainMapper
import com.sasaj.data.common.UserDtoToDomainMapper
import com.sasaj.data.httpclient.PageLinks
import com.sasaj.data.httpclient.RetrofitClient
import com.sasaj.data.httpclient.entities.ContributorDto
import com.sasaj.data.httpclient.entities.RepositoryDto
import com.sasaj.data.httpclient.entities.UserDto
import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteRepository(private val httpClient: RetrofitClient,
                       private val repositoryDtoToDomainMapper: RepositoryDtoToDomainMapper,
                       private val userDtoToDomainMapper: UserDtoToDomainMapper,
                       private val contributorDtoToDomainMapper: ContributorDtoToDomainMapper) {

    fun getPublicRepositories(since: Long,
                              onSuccess: (repos: List<GithubRepository>) -> Unit,
                              onError: (error: String) -> Unit) {

        httpClient.getRepositories(since).enqueue(object : Callback<List<RepositoryDto>> {
            override fun onFailure(call: Call<List<RepositoryDto>>?, t: Throwable?) {
                Log.d(TAG, "fail to get data")
                onError(t!!.message ?: "unknown error")
            }

            override fun onResponse(call: Call<List<RepositoryDto>>?, response: Response<List<RepositoryDto>>) {
                Log.d(TAG, "got a response $response")
                if (response.isSuccessful) {
                    val repos = response.body() ?: emptyList()
                    onSuccess(repos.map { list -> repositoryDtoToDomainMapper.mapFrom(list) })
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        }
        )
    }

    fun getSingleRepository(username: String, repositoryName: String): Observable<GithubRepository> {
        return httpClient.getRepository(username, repositoryName)
                .map { repositoryDto -> repositoryDtoToDomainMapper.mapFrom(repositoryDto) }
                .toObservable()
    }


    fun getContributors(url: String,
                        onSuccess: (contributors: List<Contributor>, first: String?, prev: String?, next: String?, last: String?) -> Unit,
                        onError: (error: String) -> Unit) {

        httpClient.getContributors(url).enqueue(object : Callback<List<ContributorDto>> {
            override fun onFailure(call: Call<List<ContributorDto>>?, t: Throwable?) {
                Log.d(TAG, "fail to get data")
                onError(t!!.message ?: "unknown error")
            }

            override fun onResponse(call: Call<List<ContributorDto>>?, response: Response<List<ContributorDto>>) {
                Log.d(TAG, "got a response $response")
                if (response.isSuccessful) {
                    val contributors = response.body() ?: emptyList()
                    val links = PageLinks((response.headers().get("Link")))
                    onSuccess(contributors.map { list -> contributorDtoToDomainMapper.mapFrom(list) }, links.first, links.prev, links.next, links.last)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        }
        )
    }

    fun getStargazers(url: String,
                      onSuccess: (stargazers: List<User>, first: String?, prev: String?, next: String?, last: String?) -> Unit,
                      onError: (error: String) -> Unit) {

        httpClient.getStargazers(url).enqueue(object : Callback<List<UserDto>> {
            override fun onFailure(call: Call<List<UserDto>>?, t: Throwable?) {
                Log.d(TAG, "fail to get data")
                onError(t!!.message ?: "unknown error")
            }

            override fun onResponse(call: Call<List<UserDto>>?, response: Response<List<UserDto>>) {
                Log.d(TAG, "got a response $response")
                if (response.isSuccessful) {
                    val stargazers = response.body() ?: emptyList()
                    val links = PageLinks((response.headers().get("Link")))
                    onSuccess(stargazers.map { list -> userDtoToDomainMapper.mapFrom(list) }, links.first, links.prev, links.next, links.last)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        }
        )

    }


    companion object {
        const val TAG: String = "RemoteRepository"
    }

}