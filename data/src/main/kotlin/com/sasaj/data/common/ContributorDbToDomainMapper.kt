package com.sasaj.data.common

import com.sasaj.data.database.entities.ContributorDb
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.Contributor

class ContributorDbToDomainMapper : Mapper<ContributorDb, Contributor>() {

    override fun mapFrom(from: ContributorDb): Contributor {
        return Contributor(
                id = from.id,
                login = from.login,
                name = from.name,
                avatarUrl = from.avatarUrl,
                url = from.url,
                htmlUrl = from.htmlUrl,
                contributions = from.contributions)

    }

    fun mapFrom(from: List<ContributorDb>): List<Contributor> {
        val contributors: MutableList<Contributor> = mutableListOf()
        from.forEach { contributorDb -> contributors.add(mapFrom(contributorDb)) }
        return contributors.toList()
    }
}