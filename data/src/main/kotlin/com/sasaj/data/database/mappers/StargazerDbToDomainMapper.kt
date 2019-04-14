package com.sasaj.data.database.mappers

import com.sasaj.data.database.entities.StargazerDb
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.User

class StargazerDbToDomainMapper : Mapper<StargazerDb, User>() {

    override fun mapFrom(from: StargazerDb): User {
        return User(
                id = from.id,
                login = from.login,
                avatarUrl = from.avatarUrl,
                url = from.url,
                htmlUrl = from.htmlUrl,
                contributions = 0)
    }

    fun mapFrom(from: List<StargazerDb>): List<User> {
        val stargazers: MutableList<User> = mutableListOf()
        from.forEach { stargazerDb -> stargazers.add(mapFrom(stargazerDb)) }
        return stargazers.toList()
    }
}