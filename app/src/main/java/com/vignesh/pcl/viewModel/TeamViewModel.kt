package com.vignesh.pcl.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vignesh.pcl.model.TeamEntity
import com.vignesh.pcl.repository.TeamRepository
import com.vignesh.pcl.roomdatabase.LocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TeamViewModel (private val application: Application): ViewModel() {

    private val repository: TeamRepository

    init {
        val teamDao = LocalDatabase.getDatabase(application).teamDao()
        repository = TeamRepository(teamDao)
    }

    fun insertTeam(team: TeamEntity) = viewModelScope.launch {
        repository.insertTeam(team)
    }

    fun getTeamsByTournamentId(tournamentId: Long): LiveData<List<TeamEntity>> {
        return repository.getTeamsByTournamentId(tournamentId)
    }

    fun getTeamById(teamId: Long): LiveData<TeamEntity> {
        return repository.getTeamById(teamId)
    }
    fun getTeamsList(tournamentId: Long):List<TeamEntity>{
        var list = emptyList<TeamEntity>()
        viewModelScope.async {
            list =  repository.getTeamsList(tournamentId)
        }
      return list
    }
}