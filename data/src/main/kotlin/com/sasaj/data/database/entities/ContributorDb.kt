package com.sasaj.data.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sasaj.data.database.GITHUB_CONTRIBUTORS_TABLE_NAME


@Entity(tableName = GITHUB_CONTRIBUTORS_TABLE_NAME)
data class ContributorDb(
        @PrimaryKey val id: Long = 0,
        val login: String?,
        val avatarUrl: String?,
        val url: String?,
        val htmlUrl: String?,
        val contributions: Int = 0
)