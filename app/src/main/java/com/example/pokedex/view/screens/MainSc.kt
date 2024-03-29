package com.example.pokedex.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pokedex.view.ui_components.PokemonInfoCard
import com.example.pokedex.view.ui_components.SearchBox
import com.example.pokedex.viewmodel.data.PokemonDataForInfoCard

@Composable
fun MainScreen(
    data: List<PokemonDataForInfoCard>
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        SearchBox()

        if(data.isNotEmpty()) {
            LazyColumn {
                items(data.size) { index ->
                    PokemonInfoCard(data[index])
                }
            }
        }
    }
}