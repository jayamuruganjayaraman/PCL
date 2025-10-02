package com.vignesh.pcl.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vignesh.pcl.model.MatchEntity

@Dao
interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: MatchEntity): Long

    @Query("SELECT * FROM MatchEntity")
    fun getAllMatches(): LiveData<List<MatchEntity>>

    @Query("SELECT * FROM MatchEntity WHERE tournamentId = :tournamentId")
    fun getMatchesByTournamentId(tournamentId: Long): LiveData<List<MatchEntity>>

    @Query("SELECT * FROM MatchEntity WHERE matchId = :matchId LIMIT 1")
    fun getMatchById(matchId: Long): LiveData<MatchEntity>
}