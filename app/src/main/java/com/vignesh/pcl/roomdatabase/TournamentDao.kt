package com.vignesh.pcl.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vignesh.pcl.model.TournamentEntity

@Dao
interface TournamentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTournament(tournament: TournamentEntity)

    @Query("SELECT * FROM tournaments ORDER BY startDate")
    fun getAllTournaments(): LiveData<List<TournamentEntity>>
}