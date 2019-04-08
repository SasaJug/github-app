package com.sasaj.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.sasaj.data.database.dao.GitHubRepositoryDao
import com.sasaj.data.database.entities.GithubRepositoryDb

@Database(entities = [(GithubRepositoryDb::class)], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun gitHubRepositoryDao() : GitHubRepositoryDao
}