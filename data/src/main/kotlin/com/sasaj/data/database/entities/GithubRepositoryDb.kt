package com.sasaj.data.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sasaj.data.database.GITHUB_REPOSITORY_TABLE_NAME

@Entity(tableName = GITHUB_REPOSITORY_TABLE_NAME)
data class GithubRepositoryDb(
        @PrimaryKey val id: Long = 0,
        val name: String?,
        val fullName: String?,
        val ownerName: String?,
        val description: String?,
        val size: String?,
        val forksCount: Int?,
        val stargazersCount: Int?,
        val stargazersUrl: String?,
        val contributorsUrl: String?,
        val htmlUrl: String?
)
