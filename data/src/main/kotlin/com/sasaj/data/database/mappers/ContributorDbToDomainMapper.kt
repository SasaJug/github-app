package com.sasaj.data.database.mappers

import com.sasaj.data.database.entities.ContributorDb
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.User

class ContributorDbToDomainMapper : Mapper<ContributorDb, User>() {

    override fun mapFrom(from: ContributorDb): User {
        return User(
                id = from.id,
                login = from.login,
                avatarUrl = from.avatarUrl,
                url = from.url,
                htmlUrl = from.htmlUrl,
                contributions = from.contributions)

    }

    fun mapFrom(from: List<ContributorDb>): List<User> {
        val contributors: MutableList<User> = mutableListOf()
        from.forEach { contributorDb -> contributors.add(mapFrom(contributorDb)) }
        return contributors.toList()
    }
}