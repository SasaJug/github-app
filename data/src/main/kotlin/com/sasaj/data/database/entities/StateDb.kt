package com.sasaj.data.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sasaj.data.database.GITHUB_STATE_TABLE_NAME

@Entity(tableName = GITHUB_STATE_TABLE_NAME)
data class StateDb(@PrimaryKey val id: Int,
                   val first: String?,
                   val prev: String?,
                   val next: String?,
                   val last: String?) {
}