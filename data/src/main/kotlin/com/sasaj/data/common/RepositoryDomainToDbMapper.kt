package com.sasaj.data.common

import com.sasaj.data.database.entities.GithubRepositoryDb
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.GithubRepository

class RepositoryDomainToDbMapper : Mapper<GithubRepository, GithubRepositoryDb>() {

    override fun mapFrom(from: GithubRepository): GithubRepositoryDb {
        return GithubRepositoryDb(
                id = from.id,
                name = from.name,
                fullName = from.fullName,
                ownerName = from.ownerName!!,
                description = from.description,
                size = from.size,
                forksCount = from.forksCount,
                stargazersCount = from.stargazersCount,
                stargazersUrl = from.stargazersUrl,
                contributorsUrl = from.contributorsUrl,
                htmlUrl = from.htmlUrl
        )
    }

    fun mapFrom(from: List<GithubRepository>): List<GithubRepositoryDb> {
        val repositories: MutableList<GithubRepositoryDb> = mutableListOf()
        from.forEach { gitHubRepository -> repositories.add(mapFrom(gitHubRepository)) }
        return repositories.toList()
    }
}