package com.vignesh.pcl.repository

import androidx.lifecycle.LiveData
import com.vignesh.pcl.model.MatchEntity
import com.vignesh.pcl.roomdatabase.MatchDao

class MatchRepository (private val matchDao: MatchDao) {

    suspend fun insertMatch(match: MatchEntity): Long {
        return matchDao.insertMatch(match)
    }

    fun getAllMatches(): LiveData<List<MatchEntity>> {
        return matchDao.getAllMatches()
    }

    fun getMatchesByTournamentId(tournamentId: Long): LiveData<List<MatchEntity>> {
        return matchDao.getMatchesByTournamentId(tournamentId)
    }

    fun getMatchById(matchId: Long): LiveData<MatchEntity> {
        return matchDao.getMatchById(matchId)
    }
}