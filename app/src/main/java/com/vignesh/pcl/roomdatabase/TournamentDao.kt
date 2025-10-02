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
     fun insert(tournament: TournamentEntity):Long

    @Query("SELECT * FROM tournaments ORDER BY startDate DESC")
    fun getAllTournaments(): LiveData<List<TournamentEntity>>

    @Query("SELECT * FROM tournaments WHERE tournamentId = :id LIMIT 1")
    fun getTournamentById(id: Long): LiveData<TournamentEntity?>
}