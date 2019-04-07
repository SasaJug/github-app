package com.sasaj.domain.entities

data class GithubRepository (

        val name : String? = "N/A",
        val fullName : String? = "N/A",
        val ownerName : String? = "N/A",
        val description : String? = "N/A",
        val size : String? = "N/A",
        val forksCount : Int? = 0,
        val stargazersCount : Int? = 0,
        val stargazersUrl: String? = "N/A",
        val contributorsUrl : String? = "N/A",
        val htmlUrl : String? = "N/A"

)