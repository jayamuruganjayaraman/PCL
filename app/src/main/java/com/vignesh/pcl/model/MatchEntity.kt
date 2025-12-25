package com.vignesh.pcl.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = TournamentEntity::class,
        parentColumns = ["tournamentId"],
        childColumns = ["tournamentId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MatchEntity(
    @PrimaryKey(autoGenerate = true) val matchId: Long = 0,
    val tournamentId: Long,
    val roundNumber: Int,
    val matchNumber: Int,
    val teamA: String,
    val teamB: String,
    val location: String,
    val matchTime: Long // Store in millis
)