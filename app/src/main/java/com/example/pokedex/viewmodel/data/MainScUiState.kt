package com.example.pokedex.viewmodel.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import com.example.pokedex.model.data.api_response.AbilityData
import com.example.pokedex.model.data.api_response.FlavorText
import com.example.pokedex.model.data.api_response.PokemonData
import kotlinx.coroutines.flow.Flow

data class MainScUiState(
    val pokeDataList: Flow<PagingData<List<PokemonData>>>,
    val searchText: MutableState<String> = mutableStateOf(""),
    val isShowDetail: Boolean = false,
    val pokeDetailScUiState: PokeDetailScUiState = PokeDetailScUiState(),
)