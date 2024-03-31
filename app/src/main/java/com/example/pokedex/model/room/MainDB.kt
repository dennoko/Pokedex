package com.example.pokedex.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MainEntity::class], version = 1, exportSchema = false)
abstract class MainDB: RoomDatabase() {
    abstract fun mainDao(): MainDao

    companion object {
        private var INSTANCE: MainDB? = null

        fun getDB(context: Context): MainDB {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    MainDB::class.java,
                    "main_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}