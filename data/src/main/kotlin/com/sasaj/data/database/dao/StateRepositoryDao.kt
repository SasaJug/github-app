package com.sasaj.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sasaj.data.database.entities.StateDb
import io.reactivex.Flowable


@Dao
interface StateRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stateDb: StateDb)

    @Query("SELECT * FROM github_state_table WHERE id = :id")
    fun getState(id: Int): Flowable<StateDb>

    @Query("DELETE FROM github_state_table WHERE id = :id")
    fun delete(id: Int)
}