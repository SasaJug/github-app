package com.sasaj.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sasaj.data.database.entities.GithubRepositoryDb
import io.reactivex.Flowable


@Dao
interface GitHubRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repositories: List<GithubRepositoryDb>)

    @Query("SELECT * FROM github_repo_table")
    fun getRepositories(): Flowable<List<GithubRepositoryDb>>

    @Query("DELETE FROM github_repo_table")
    fun deleteAll()
}