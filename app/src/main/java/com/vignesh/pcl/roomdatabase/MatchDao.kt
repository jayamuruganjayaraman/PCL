package com.vignesh.pcl.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vignesh.pcl.model.MatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matches: List<MatchEntity>)

    @Query("SELECT * FROM MatchEntity WHERE tournamentId = :tournamentId ORDER BY roundNumber, matchNumber")
    fun getMatchesByTournament(tournamentId: Long): Flow<List<MatchEntity>>
}