package com.vignesh.pcl.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vignesh.pcl.model.PlayerEntity
import com.vignesh.pcl.repository.PlayerRepository
import com.vignesh.pcl.roomdatabase.LocalDatabase
import kotlinx.coroutines.launch

class PlayerViewModel(private val application: Application): ViewModel() {
    private val repository: PlayerRepository

    init {
        val playerDao = LocalDatabase.getDatabase(application).playerDao()
        repository = PlayerRepository(playerDao)
    }

    fun insertPlayer(player: PlayerEntity) = viewModelScope.launch {
        repository.insertPlayer(player)
    }

    fun getAllPlayers(): LiveData<List<PlayerEntity>> = repository.getAllPlayers()

    fun getPlayersByTeamId(teamId: Long): LiveData<List<PlayerEntity>> {
        return repository.getPlayersByTeamId(teamId)
    }

    fun getPlayerById(playerId: Long): LiveData<PlayerEntity> {
        return repository.getPlayerById(playerId)
    }
}