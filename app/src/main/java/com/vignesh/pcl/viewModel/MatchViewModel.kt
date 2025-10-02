package com.vignesh.pcl.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vignesh.pcl.model.MatchEntity
import com.vignesh.pcl.repository.MatchRepository
import com.vignesh.pcl.roomdatabase.LocalDatabase
import kotlinx.coroutines.launch

class MatchViewModel (private val application: Application): ViewModel() {
    private val repository: MatchRepository

    init {
        val matchDao = LocalDatabase.getDatabase(application).matchDao()
        repository = MatchRepository(matchDao)
    }

    fun insertMatch(match: MatchEntity) = viewModelScope.launch {
        repository.insertMatch(match)
    }

    fun getAllMatches(): LiveData<List<MatchEntity>> = repository.getAllMatches()

    fun getMatchesByTournamentId(tournamentId: Long): LiveData<List<MatchEntity>> {
        return repository.getMatchesByTournamentId(tournamentId)
    }

    fun getMatchById(matchId: Long): LiveData<MatchEntity> {
        return repository.getMatchById(matchId)
    }
}