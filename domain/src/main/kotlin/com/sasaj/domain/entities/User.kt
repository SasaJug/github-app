package com.sasaj.domain.entities

data class User(
        val id: Long,
        val login: String?,
        val avatarUrl: String?,
        val url: String?,
        val htmlUrl: String?,
        val contributions: Int
)