package com.vignesh.pcl.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vignesh.pcl.model.PlayerEntity

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity): Long

    @Query("SELECT * FROM PlayerEntity")
    fun getAllPlayers(): LiveData<List<PlayerEntity>>

    @Query("SELECT * FROM PlayerEntity WHERE teamId = :teamId")
    fun getPlayersByTeamId(teamId: Long): LiveData<List<PlayerEntity>>

    @Query("SELECT * FROM PlayerEntity WHERE playerId = :playerId LIMIT 1")
    fun getPlayerById(playerId: Long): LiveData<PlayerEntity>
}