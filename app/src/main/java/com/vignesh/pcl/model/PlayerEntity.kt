package com.vignesh.pcl.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (
    foreignKeys = [ForeignKey(
        entity = TeamEntity::class,
        parentColumns = ["teamId"],
        childColumns = ["teamId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PlayerEntity (
    @PrimaryKey(autoGenerate = true) val playerId: Long = 0,
    val name: String,
    val teamId: Long
)