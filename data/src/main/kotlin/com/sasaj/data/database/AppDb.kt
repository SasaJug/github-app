package com.sasaj.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.sasaj.data.database.dao.ContributorRepositoryDao
import com.sasaj.data.database.dao.GitHubRepositoryDao
import com.sasaj.data.database.dao.StargazerRepositoryDao
import com.sasaj.data.database.dao.StateRepositoryDao
import com.sasaj.data.database.entities.ContributorDb
import com.sasaj.data.database.entities.GithubRepositoryDb
import com.sasaj.data.database.entities.StargazerDb
import com.sasaj.data.database.entities.StateDb

@Database(entities = [(GithubRepositoryDb::class),(StargazerDb::class),(ContributorDb::class), (StateDb::class)], version = 5, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun gitHubRepositoryDao() : GitHubRepositoryDao
    abstract fun contributorDao() : ContributorRepositoryDao
    abstract fun stargazerDao() : StargazerRepositoryDao
    abstract fun stateDao() : StateRepositoryDao
}