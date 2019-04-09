package com.sasaj.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sasaj.data.database.entities.ContributorDb
import com.sasaj.data.database.entities.GithubRepositoryDb
import io.reactivex.Flowable


@Dao
interface ContributorRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contributors: List<ContributorDb>)

    @Query("SELECT * FROM github_contributor_table")
    fun getContributors(): Flowable<List<ContributorDb>>

    @Query("DELETE FROM github_contributor_table")
    fun deleteAll()
}