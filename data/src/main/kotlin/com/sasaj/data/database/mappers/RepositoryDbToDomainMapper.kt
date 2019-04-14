package com.sasaj.data.database.mappers

import com.sasaj.data.database.entities.GithubRepositoryDb
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.GithubRepository

class RepositoryDbToDomainMapper : Mapper<GithubRepositoryDb, GithubRepository>() {

    override fun mapFrom(from: GithubRepositoryDb): GithubRepository {
        return GithubRepository(
                id = from.id,
                name = from.name,
                fullName = from.fullName,
                ownerName = from.ownerName,
                description = from.description,
                size = from.size,
                forksCount = from.forksCount,
                stargazersCount = from.stargazersCount,
                stargazersUrl = from.stargazersUrl,
                contributorsUrl = from.contributorsUrl,
                htmlUrl = from.htmlUrl
        )
    }

    fun mapFrom(from: List<GithubRepositoryDb>): List<GithubRepository> {
        val repositories: MutableList<GithubRepository> = mutableListOf()
        from.forEach { gitHubRepositoryDb -> repositories.add(mapFrom(gitHubRepositoryDb)) }
        return repositories.toList()
    }
}