package com.example.pokedex.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {
    @Insert
    suspend fun insert(mainEntity: MainEntity)

    // id で検索して MainEntity を返す
    @Query("SELECT * FROM MainEntity WHERE id = :id")
    suspend fun getMainEntity(id: Int): MainEntity

    // 全ての MainEntity を返す
    @Query("SELECT * FROM MainEntity")
    fun getAllMainEntity(): Flow<List<MainEntity>>
}