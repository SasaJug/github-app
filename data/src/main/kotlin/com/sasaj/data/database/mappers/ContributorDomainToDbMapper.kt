package com.sasaj.data.database.mappers

import com.sasaj.data.database.entities.ContributorDb
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.User

class ContributorDomainToDbMapper : Mapper<User, ContributorDb>() {

    override fun mapFrom(from: User): ContributorDb {
        return ContributorDb(
                id = from.id,
                login = from.login,
                avatarUrl = from.avatarUrl,
                url = from.url,
                htmlUrl = from.htmlUrl,
                contributions = from.contributions)

    }

    fun mapFrom(from: List<User>): List<ContributorDb> {
        val contributors: MutableList<ContributorDb> = mutableListOf()
        from.forEach { contributor -> contributors.add(mapFrom(contributor)) }
        return contributors.toList()
    }
}