package com.sasaj.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sasaj.data.database.entities.ContributorDb
import com.sasaj.data.database.entities.GithubRepositoryDb
import com.sasaj.data.database.entities.StargazerDb
import io.reactivex.Flowable


@Dao
interface StargazerRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stargazers: List<StargazerDb>)

    @Query("SELECT * FROM github_stargazer_table")
    fun getStargazers(): Flowable<List<StargazerDb>>

    @Query("DELETE FROM github_stargazer_table")
    fun deleteAll()
}