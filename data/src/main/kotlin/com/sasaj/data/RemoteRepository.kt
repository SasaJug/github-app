package com.sasaj.data

import android.util.Log
import com.sasaj.data.common.ContributorDtoToDomainMapper
import com.sasaj.data.common.RepositoryDtoToDomainMapper
import com.sasaj.data.common.UserDtoToDomainMapper
import com.sasaj.data.entities.RepositoryDto
import com.sasaj.data.httpclient.RetrofitClient
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

    fun getStargazersForRepository(username: String, repositoryName: String): Observable<List<User>> {
        return httpClient.getStargazersForRepository(username, repositoryName)
                .map { userDto -> userDtoToDomainMapper.mapFrom(userDto) }
                .toObservable()
    }

    fun getContributorsForRepository(username: String, repositoryName: String): Observable<List<Contributor>> {
        return httpClient.getContributorsForRepository(username, repositoryName)
                .map { contributorDto -> contributorDtoToDomainMapper.mapFrom(contributorDto) }
                .toObservable()
    }

    companion object {
        const val TAG : String = "RemoteRepository"
    }

}