package com.sasaj.data.common

import com.sasaj.data.httpclient.entities.UserDto
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.User

class UserDtoToDomainMapper : Mapper<UserDto, User>() {
    override fun mapFrom(from: UserDto): User {
        return User(
                id = from.id,
                login = from.login,
                avatarUrl = from.avatar_url,
                url = from.url,
                htmlUrl = from.html_url
        )
    }


    fun mapFrom(from: List<UserDto>): List<User> {
        val users: MutableList<User> = mutableListOf()
        from.forEach { userDto -> users.add(mapFrom(userDto)) }
        return users.toList()
    }
}