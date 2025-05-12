package com.vignesh.pcl.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(entity = MatchEntity::class, parentColumns = ["matchId"], childColumns = ["matchId"], onDelete = CASCADE),
        ForeignKey(entity = PlayerEntity::class, parentColumns = ["playerId"], childColumns = ["playerId"], onDelete = CASCADE)
    ]
)
data class PlayerStatsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val matchId: Long,
    val playerId: Long,
    val innings: Int = 1,  // useful in multi-innings games

    // Batting
    val runs: Int,
    val ballsFaced: Int,
    val sixes: Int,
    val fours: Int,
    val isFifty: Boolean = false,
    val isCentury: Boolean = false,

    // Bowling
    val wickets: Int,
    val oversBowled: Float,

    // Fielding
    val catches: Int
)