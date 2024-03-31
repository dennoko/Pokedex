package com.example.pokedex.model.room

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MainDao {
    @Insert
    suspend fun insert(mainEntity: MainEntity)
}