package com.vignesh.pcl.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.vignesh.pcl.model.MatchEntity
import com.vignesh.pcl.model.PlayerEntity
import com.vignesh.pcl.model.PlayerStatsEntity
import com.vignesh.pcl.model.TeamEntity
import com.vignesh.pcl.model.TeamStatsEntity
import com.vignesh.pcl.model.TournamentEntity

@Database(
    entities = [
        TournamentEntity::class,
        TeamEntity::class,
        PlayerEntity::class,
        MatchEntity::class,
        PlayerStatsEntity::class,
        TeamStatsEntity::class
    ],
    version = 1
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun tournamentDao(): TournamentDao
    companion object {
        val DB_NAME: String = "pcl.db"
        @Volatile
        private var INSTANCE: LocalDatabase? = null
        fun getDatabase(context: Context): LocalDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    DB_NAME)
                    //.addMigrations(MIGRATION_5_6,MIGRATION_6_7,MIGRATION_7_8,MIGRATION_8_9,MIGRATION_9_10)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }
}