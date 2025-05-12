package com.vignesh.pcl.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MatchEntity (
    @PrimaryKey(autoGenerate = true) val matchId: Long = 0,
    val tournamentId: Long,
    val teamAId: Long,
    val teamBId: Long,
    val date: String
)