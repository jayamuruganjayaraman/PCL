package com.vignesh.pcl.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = PlayerEntity::class,
        parentColumns = ["playerId"],
        childColumns = ["playerId"],
        onDelete = CASCADE
    )]
)
data class PlayerCumulativeStatsEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val playerId: Long,
    val totalMatches: Int,
    val totalInnings: Int,
    val totalRuns: Int,
    val totalBalls: Int,
    val totalSixes: Int,
    val totalFours: Int,
    val fifties: Int,
    val centuries: Int,
    val totalWickets: Int,
    val totalOvers: Float,
    val totalCatches: Int
)