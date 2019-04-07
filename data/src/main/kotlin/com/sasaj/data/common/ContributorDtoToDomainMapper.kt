package com.sasaj.data.common

import com.sasaj.data.entities.ContributorDto
import com.sasaj.data.entities.UserDto
import com.sasaj.domain.common.Mapper
import com.sasaj.domain.entities.Contributor
import com.sasaj.domain.entities.User

class ContributorDtoToDomainMapper : Mapper<ContributorDto, Contributor>() {
    override fun mapFrom(from: ContributorDto): Contributor{
        return Contributor(
                login = from.login,
                avatarUrl = from.avatar_url,
                url = from.url,
                htmlUrl = from.html_url,
                contributions = from.contributions
        )
    }

    fun mapFrom(from : List<ContributorDto>) : List<Contributor>{
        val contributors : MutableList<Contributor> = mutableListOf()
        from.forEach { contributorDto -> contributors.add(mapFrom(contributorDto))}
        return contributors.toList()
    }
}