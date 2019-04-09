package com.sasaj.data.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sasaj.data.database.GITHUB_STARGAZERS_TABLE_NAME

@Entity(tableName = GITHUB_STARGAZERS_TABLE_NAME)
data class StargazerDb(
        @PrimaryKey val id: Long,
        val login: String?,
        val name: String?,
        val avatarUrl: String?,
        val url: String?,
        val htmlUrl: String?
)