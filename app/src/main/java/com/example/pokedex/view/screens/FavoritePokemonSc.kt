package com.example.pokedex.view.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.pokedex.view.ui_components.PokemonInfoCard
import com.example.pokedex.viewmodel.data.FavoritePokemonScUiState

@Composable
fun FavoritePokemonSc(
    uiState: FavoritePokemonScUiState = FavoritePokemonScUiState(),
    init: () -> Unit,
    infoCardClicked: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        init()
    }
    
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        if (uiState.favoritePokemonList != null) {
            // Display the list of favorite Pokemon
            uiState.favoritePokemonList.forEach {
                // Display the PokemonInfoCard
                PokemonInfoCard(
                    data = it,
                    infoCardClicked = {
                        infoCardClicked(it.id)
                    }
                )
            }
        }    
    }
}