package com.sasaj.domain.entities

data class User(
        val id: Long = 0,
        val login: String? = "N/A",
        val name: String? = "N/A",
        val avatarUrl: String? = "N/A",
        val url: String? = "N/A",
        val htmlUrl: String? = "N/A"
) : GitHubUser