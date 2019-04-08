package com.sasaj.data.common

import com.sasaj.data.entities.RepositoryDto
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.GithubRepository

class RepositoryDtoToDomainMapper : Mapper<RepositoryDto, GithubRepository>() {

    override fun mapFrom(from: RepositoryDto): GithubRepository {
        return GithubRepository(
                id = from.id,
                name = from.name,
                fullName = from.full_name,
                ownerName = from.owner.login,
                description = from.description,
                size = from.size.toString(),
                forksCount = from.forks_count,
                stargazersCount = from.stargazers_count,
                stargazersUrl = from.stargazers_url,
                contributorsUrl = from.contributors_url,
                htmlUrl = from.html_url
        )
    }

    fun mapFrom(from: List<RepositoryDto>): List<GithubRepository> {
        val repositories: MutableList<GithubRepository> = mutableListOf()
        from.forEach { repositoryDto -> repositories.add(mapFrom(repositoryDto)) }
        return repositories.toList()
    }
}