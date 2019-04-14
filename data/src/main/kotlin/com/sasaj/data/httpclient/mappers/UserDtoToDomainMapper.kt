package com.sasaj.data.httpclient.mappers

import com.sasaj.data.httpclient.entities.StargazerDto
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.User

class UserDtoToDomainMapper : Mapper<StargazerDto, User>() {
    override fun mapFrom(from: StargazerDto): User {
        return User(
                id = from.id,
                login = from.login,
                avatarUrl = from.avatar_url,
                url = from.url,
                htmlUrl = from.html_url,
                contributions = 0
        )
    }


    fun mapFrom(from: List<StargazerDto>): List<User> {
        val users: MutableList<User> = mutableListOf()
        from.forEach { userDto -> users.add(mapFrom(userDto)) }
        return users.toList()
    }
}