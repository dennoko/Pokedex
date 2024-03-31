package com.example.pokedex.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MainEntity::class], version = 1, exportSchema = false)
abstract class MainDB: RoomDatabase() {
    abstract fun mainDao(): MainDao

    companion object {
        private var INSTANCE: MainDB? = null

        fun getdb(context: Context): MainDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MainDB::class.java,
                    "main_db"
                ).build()
            }

            return INSTANCE!!
        }
    }
}