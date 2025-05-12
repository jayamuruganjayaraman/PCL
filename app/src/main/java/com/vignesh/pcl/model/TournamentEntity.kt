package com.vignesh.pcl.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tournaments")
data class TournamentEntity (
    @PrimaryKey(autoGenerate = true) val tournamentId: Long = 0,
    val name: String,
    val location: String,
    val startDate: Long,
    val endDate: Long,
    val scheduleState: Boolean
)