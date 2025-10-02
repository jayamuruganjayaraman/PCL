package com.vignesh.pcl.repository

import androidx.lifecycle.LiveData
import com.vignesh.pcl.model.PlayerEntity
import com.vignesh.pcl.roomdatabase.PlayerDao

class PlayerRepository (private val playerDao: PlayerDao) {

    suspend fun insertPlayer(player: PlayerEntity): Long {
        return playerDao.insertPlayer(player)
    }

    fun getAllPlayers(): LiveData<List<PlayerEntity>> {
        return playerDao.getAllPlayers()
    }

    fun getPlayersByTeamId(teamId: Long): LiveData<List<PlayerEntity>> {
        return playerDao.getPlayersByTeamId(teamId)
    }

    fun getPlayerById(playerId: Long): LiveData<PlayerEntity> {
        return playerDao.getPlayerById(playerId)
    }
}