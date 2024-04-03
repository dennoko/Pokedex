package com.example.pokedex.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imgUrl: String,
    val type1: String,
    val type2: String? = null,
)
