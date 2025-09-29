package com.vignesh.pcl.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.vignesh.pcl.model.TournamentEntity
import com.vignesh.pcl.roomdatabase.LocalDatabase
import com.vignesh.pcl.roomdatabase.TournamentDao

class TournamentRepository(private val tournamentDao: TournamentDao) {

    suspend fun insert(tournament: TournamentEntity):Long {
        return tournamentDao.insert(tournament)
    }

    val allTournaments: LiveData<List<TournamentEntity>> = tournamentDao.getAllTournaments()
}