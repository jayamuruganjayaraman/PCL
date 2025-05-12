package com.vignesh.pcl.repository

import android.content.Context
import com.vignesh.pcl.model.TournamentEntity
import com.vignesh.pcl.roomdatabase.LocalDatabase
import com.vignesh.pcl.roomdatabase.TournamentDao

class TournamentRepository(private val mContext: Context) {
    private val tournamentDao: TournamentDao

    init {
        val db: LocalDatabase = LocalDatabase.getDatabase(mContext)
        tournamentDao = db.tournamentDao()
    }

    suspend fun insert(tournament: TournamentEntity) {
        tournamentDao.insertTournament(tournament)
    }
}