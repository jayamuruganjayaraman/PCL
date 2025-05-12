package com.vignesh.pcl.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamStatsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val matchId: Long,
    val teamId: Long,
    val totalRuns: Int,
    val totalWickets: Int,
    val oversPlayed: Float
)
