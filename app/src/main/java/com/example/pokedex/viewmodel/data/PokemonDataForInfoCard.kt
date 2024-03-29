package com.example.pokedex.viewmodel.data

data class PokemonDataForInfoCard(
    val name: String,
    val id: Int,
    val imgUrl: String,
    val types: List<String>,
)
