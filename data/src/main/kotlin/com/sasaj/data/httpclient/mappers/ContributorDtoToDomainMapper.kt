package com.sasaj.data.httpclient.mappers

import com.sasaj.data.httpclient.entities.ContributorDto
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.User

class ContributorDtoToDomainMapper : Mapper<ContributorDto, User>() {
    override fun mapFrom(from: ContributorDto): User{
        return User(
                id = from.id,
                login = from.login,
                avatarUrl = from.avatar_url,
                url = from.url,
                htmlUrl = from.html_url,
                contributions = from.contributions
        )
    }

    fun mapFrom(from : List<ContributorDto>) : List<User>{
        val contributors : MutableList<User> = mutableListOf()
        from.forEach { contributorDto -> contributors.add(mapFrom(contributorDto))}
        return contributors.toList()
    }
}