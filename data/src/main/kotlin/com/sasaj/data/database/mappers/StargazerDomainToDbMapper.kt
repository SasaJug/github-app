package com.sasaj.data.database.mappers

import com.sasaj.data.database.entities.StargazerDb
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.User

class StargazerDomainToDbMapper : Mapper<User, StargazerDb>() {

    override fun mapFrom(from: User): StargazerDb {
        return StargazerDb(
                id = from.id,
                login = from.login,
                avatarUrl = from.avatarUrl,
                url = from.url,
                htmlUrl = from.htmlUrl)

    }

    fun mapFrom(from: List<User>): List<StargazerDb> {
        val stargazers: MutableList<StargazerDb> = mutableListOf()
        from.forEach { stargazer -> stargazers.add(mapFrom(stargazer)) }
        return stargazers.toList()
    }
}