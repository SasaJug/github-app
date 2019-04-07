package com.sasaj.data

import com.sasaj.data.common.ContributorDtoToDomainMapper
import com.sasaj.data.common.RepositoryDtoToDomainMapper
import com.sasaj.data.common.UserDtoToDomainMapper
import com.sasaj.data.httpclient.RetrofitClient
import com.sasaj.domain.Repository
import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.entities.User
import io.reactivex.Observable

class RemoteRepository(private val httpClient: RetrofitClient,
                       private val repositoryDtoToDomainMapper: RepositoryDtoToDomainMapper,
                       private val userDtoToDomainMapper: UserDtoToDomainMapper,
                       private val contributorDtoToDomainMapper: ContributorDtoToDomainMapper) : Repository {

    override fun getPublicRepositories(): Observable<List<GithubRepository>> {
        return httpClient.getRepositories(0)
                .map { repositoryDto -> repositoryDtoToDomainMapper.mapFrom(repositoryDto) }
                .toObservable()
    }

    override fun getSingleRepository(username: String, repositoryName: String): Observable<GithubRepository> {
        return httpClient.getRepository(username, repositoryName)
                .map { repositoryDto -> repositoryDtoToDomainMapper.mapFrom(repositoryDto) }
                .toObservable()
    }

    override fun getStargazersForRepository(username: String, repositoryName: String): Observable<List<User>> {
        return httpClient.getStargazersForRepository(username, repositoryName)
                .map { userDto -> userDtoToDomainMapper.mapFrom(userDto) }
                .toObservable()
    }

    override fun getContributorsForRepository(username: String, repositoryName: String): Observable<List<Contributor>> {
        return httpClient.getContributorsForRepository(username, repositoryName)
                .map { contributorDto -> contributorDtoToDomainMapper.mapFrom(contributorDto) }
                .toObservable()    }

}