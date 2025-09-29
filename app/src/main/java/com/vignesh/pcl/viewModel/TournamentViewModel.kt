package com.vignesh.pcl.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vignesh.pcl.model.TournamentEntity
import com.vignesh.pcl.repository.TournamentRepository
import com.vignesh.pcl.roomdatabase.LocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TournamentViewModel (private val application: Application): ViewModel() {
    private val repository: TournamentRepository
    val allTournaments: LiveData<List<TournamentEntity>>

    init {
        val dataDao = LocalDatabase.getDatabase(application).tournamentDao()
        repository = TournamentRepository(dataDao)
        allTournaments = repository.allTournaments
    }

    fun insertTournament(tournament: TournamentEntity):Long {
       var  result = -1L
        viewModelScope.launch(Dispatchers.IO) {
            result = repository.insert(tournament)
        }
        return result
    }
}