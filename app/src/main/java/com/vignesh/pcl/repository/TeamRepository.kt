package com.vignesh.pcl.repository

import androidx.lifecycle.LiveData
import com.vignesh.pcl.model.TeamEntity
import com.vignesh.pcl.roomdatabase.TeamDao

class TeamRepository (private val teamDao: TeamDao) {

    suspend fun insertTeam(team: TeamEntity): Long {
        return teamDao.insertTeam(team)
    }

    fun getTeamsByTournamentId(tournamentId: Long): LiveData<List<TeamEntity>> {
        return teamDao.getTeamsByTournamentId(tournamentId)
    }

    fun getTeamById(teamId: Long): LiveData<TeamEntity> {
        return teamDao.getTeamById(teamId)
    }
}