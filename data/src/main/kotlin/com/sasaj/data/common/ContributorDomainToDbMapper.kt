package com.sasaj.data.common

import com.sasaj.data.database.entities.ContributorDb
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.Contributor

class ContributorDomainToDbMapper : Mapper<Contributor, ContributorDb>() {

    override fun mapFrom(from: Contributor): ContributorDb {
        return ContributorDb(
                id = from.id,
                login = from.login,
                name = from.name,
                avatarUrl = from.avatarUrl,
                url = from.url,
                htmlUrl = from.htmlUrl,
                contributions = from.contributions)

    }

    fun mapFrom(from: List<Contributor>): List<ContributorDb> {
        val contributors: MutableList<ContributorDb> = mutableListOf()
        from.forEach { contributor -> contributors.add(mapFrom(contributor)) }
        return contributors.toList()
    }
}