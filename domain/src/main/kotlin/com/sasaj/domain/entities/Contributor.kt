package com.sasaj.domain.entities

data class Contributor(
        val login: String = "N/A",
        val name: String = "N/A",
        val avatarUrl: String = "N/A",
        val url: String = "N/A",
        val htmlUrl: String = "N/A",
        val contributions: Int = 0
)