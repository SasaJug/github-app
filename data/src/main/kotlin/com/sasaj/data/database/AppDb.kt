package com.sasaj.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.sasaj.data.database.dao.ContributorRepositoryDao
import com.sasaj.data.database.dao.GitHubRepositoryDao
import com.sasaj.data.database.dao.StargazerRepositoryDao
import com.sasaj.data.database.entities.ContributorDb
import com.sasaj.data.database.entities.GithubRepositoryDb
import com.sasaj.data.database.entities.StargazerDb

@Database(entities = [(GithubRepositoryDb::class),(StargazerDb::class),(ContributorDb::class)], version = 2, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun gitHubRepositoryDao() : GitHubRepositoryDao
    abstract fun contributorDao() : ContributorRepositoryDao
    abstract fun stargazerDao() : StargazerRepositoryDao
}