package com.example.pokedex.viewmodel.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.pokedex.model.data.api_response.AbilityData
import com.example.pokedex.model.data.api_response.PokemonData

data class PokeDetailScUiState(
    val data: PokemonData? = null,
    val gifUrl: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/1.gif",
    val isFavorite: MutableState<Boolean> = mutableStateOf(false),
    val flavorText: String = "",
    val abilities: List<AbilityData> = emptyList(),
)
