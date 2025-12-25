package com.vignesh.pcl.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vignesh.pcl.model.MatchEntity
import com.vignesh.pcl.repository.MatchRepository
import com.vignesh.pcl.roomdatabase.LocalDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MatchViewModel (private val application: Application): ViewModel() {
    private val repository: MatchRepository
    private val _matches = MutableStateFlow<List<MatchEntity>>(emptyList())
    val matches: StateFlow<List<MatchEntity>> = _matches
    init {
        val matchDao = LocalDatabase.getDatabase(application).matchDao()
        repository = MatchRepository(matchDao)
    }

    fun loadMatches(tournamentId: Long) {
        viewModelScope.launch {
            repository.getMatchesByTournament(tournamentId).collect { matchList ->
                _matches.value = matchList
            }
        }
    }

    fun scheduleMatches(
        tournamentId: Long,
        teamList: List<String>,
        location: String,
        startDate: Long
    ) = viewModelScope.launch {
        val matches = generateMatchSchedule(tournamentId, teamList, location, startDate)
        repository.insertMatches(matches)
    }

    private fun generateMatchSchedule(
        tournamentId: Long,
        teamList: List<String>,
        location: String,
        startDate: Long
    ): List<MatchEntity> {
        val schedule = mutableListOf<MatchEntity>()
        val oneDayMillis = 24 * 60 * 60 * 1000L
        val time7pm = 19 * 60 * 60 * 1000L // 7 PM
        var currentDate = startDate

        val rounds = generateRoundRobin(teamList)

        rounds.forEachIndexed { roundIndex, roundMatches ->
            roundMatches.forEachIndexed { matchIndex, (teamA, teamB) ->
                val matchTime = currentDate + time7pm
                schedule.add(
                    MatchEntity(
                        tournamentId = tournamentId,
                        roundNumber = roundIndex + 1,
                        matchNumber = matchIndex + 1,
                        teamA = teamA,
                        teamB = teamB,
                        location = location,
                        matchTime = matchTime
                    )
                )
            }
            currentDate += oneDayMillis // next round = next day
        }

        val semiFinal1Time = currentDate + time7pm
        val semiFinal2Time = currentDate + oneDayMillis + time7pm
        val finalTime = currentDate + (2 * oneDayMillis) + time7pm

        schedule.addAll(
            listOf(
                MatchEntity(
                    tournamentId = tournamentId,
                    roundNumber = rounds.size + 1,
                    matchNumber = 1,
                    teamA = "TBD",
                    teamB = "TBD",
                    location = location,
                    matchTime = semiFinal1Time
                ),
                MatchEntity(
                    tournamentId = tournamentId,
                    roundNumber = rounds.size + 2,
                    matchNumber = 1,
                    teamA = "TBD",
                    teamB = "TBD",
                    location = location,
                    matchTime = semiFinal2Time
                ),
                MatchEntity(
                    tournamentId = tournamentId,
                    roundNumber = rounds.size + 3,
                    matchNumber = 1,
                    teamA = "TBD",
                    teamB = "TBD",
                    location = location,
                    matchTime = finalTime
                )
            )
        )

        return schedule
    }



    private fun generateRoundRobin(teams: List<String>): List<List<Pair<String, String>>> {
        val teamList = (if (teams.size % 2 != 0) teams + "Bye" else teams).toMutableList()
        val n = teamList.size
        val rounds = mutableListOf<List<Pair<String, String>>>()

        for (round in 0 until n - 1) {
            val matches = mutableListOf<Pair<String, String>>()
            for (i in 0 until n / 2) {
                val teamA = teamList[i]
                val teamB = teamList[n - 1 - i]
                if (teamA != "Bye" && teamB != "Bye") {
                    matches.add(Pair(teamA, teamB))
                }
            }
            rounds.add(matches)
            // Rotate teams except the first one
            val rotated = teamList.toMutableList()
            val first = rotated.removeAt(1)
            rotated.add(first)
            for (i in teamList.indices) teamList[i] = rotated[i]
        }
        return rounds
    }


}