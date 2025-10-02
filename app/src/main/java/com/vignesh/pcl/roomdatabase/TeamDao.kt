package com.vignesh.pcl.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vignesh.pcl.model.TeamEntity

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: TeamEntity): Long

    @Query("SELECT * FROM TeamEntity WHERE tournamentId = :tournamentId")
    fun getTeamsByTournamentId(tournamentId: Long): LiveData<List<TeamEntity>>

    @Query("SELECT * FROM TeamEntity WHERE teamId = :teamId LIMIT 1")
    fun getTeamById(teamId: Long): LiveData<TeamEntity>
}