package com.example.pokedex.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainEntity(
    @PrimaryKey val id: Int,
    val jsonPokeData: String
)
