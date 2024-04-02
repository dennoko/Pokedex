package com.example.pokedex.viewmodel.data

import androidx.paging.PagingData
import com.example.pokedex.model.data.api_response.AbilityData
import com.example.pokedex.model.data.api_response.PokemonData
import kotlinx.coroutines.flow.Flow

data class MainScUiState(
    val pokeDataList: Flow<PagingData<List<PokemonData>>>,
    val isShowDetail: Boolean = false,
    val abilities: List<AbilityData> = emptyList(),
)