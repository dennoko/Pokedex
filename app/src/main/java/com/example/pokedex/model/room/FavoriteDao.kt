package com.example.pokedex.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insert(favoriteEntity: FavoriteEntity)

    @Update
    suspend fun update(favoriteEntity: FavoriteEntity)

    // delete FavoriteEntity from id
    @Query("DELETE FROM favoriteentity WHERE id = :id")
    suspend fun delete(id: Int)

    // get All FavoriteEntity
    @Query("SELECT * FROM favoriteentity")
    suspend fun getAll(): List<FavoriteEntity>

    // get all id of FavoriteEntity
    @Query("SELECT id FROM favoriteentity")
    suspend fun getAllId(): List<Int>

    // check id is already in FavoriteEntity. return boolean
    @Query("SELECT EXISTS(SELECT * FROM favoriteentity WHERE id = :id)")
    suspend fun isExist(id: Int): Boolean
}