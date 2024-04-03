package com.example.pokedex.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDB: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var INSTANCE: FavoriteDB? = null

        fun getInstance(context: Context): FavoriteDB {
            if (INSTANCE == null) {
                synchronized(FavoriteDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDB::class.java,
                        "favorite_db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}