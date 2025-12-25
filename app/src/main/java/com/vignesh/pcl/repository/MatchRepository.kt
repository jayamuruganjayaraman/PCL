package com.vignesh.pcl.repository

import androidx.lifecycle.LiveData
import com.vignesh.pcl.model.MatchEntity
import com.vignesh.pcl.roomdatabase.MatchDao
import kotlinx.coroutines.flow.Flow

class MatchRepository (private val matchDao: MatchDao) {

    fun getMatchesByTournament(tournamentId: Long): Flow<List<MatchEntity>> {
        return matchDao.getMatchesByTournament(tournamentId)
    }

    suspend fun insertMatches(matches: List<MatchEntity>) {
        matchDao.insertAll(matches)
    }


}