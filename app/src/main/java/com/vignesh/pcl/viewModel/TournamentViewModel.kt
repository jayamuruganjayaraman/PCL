package com.vignesh.pcl.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.vignesh.pcl.model.TournamentEntity
import com.vignesh.pcl.repository.TournamentRepository

class TournamentViewModel (private val application: Application): ViewModel() {
    private val repository: TournamentRepository
    //val allTournaments: LiveData<List<TournamentEntity>>

    init {
        repository = TournamentRepository(application.applicationContext)
       // allTournaments = repository.allTournaments
    }

    suspend fun insertTournament(tournament: TournamentEntity) {
        repository.insert(tournament)
    }
}