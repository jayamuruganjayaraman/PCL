package com.vignesh.pcl.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (
    foreignKeys = [ForeignKey(
        entity = TournamentEntity::class,
        parentColumns = ["tournamentId"],
        childColumns = ["tournamentId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TeamEntity (
    @PrimaryKey(autoGenerate = true) val teamId: Long = 0,
    val name: String,
    val tournamentId: Long
)