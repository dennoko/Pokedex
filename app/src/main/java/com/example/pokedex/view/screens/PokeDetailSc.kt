package com.example.pokedex.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.model.data.api_response.PokemonData
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.view.ui_components.PokeDetailTopBar

@Composable
fun PokeDetailSc(
    data: PokemonData?,
    isFavorite: Boolean = false,
    backIconClicked: () -> Unit = {},
    favoriteIconClicked: () -> Unit = {}
) {
    var pokeImg by remember { mutableStateOf(data?.sprites?.frontDefault ?: "") }

    Column(
        modifier = Modifier
    ) {
        PokeDetailTopBar(
            isFavorite = isFavorite,
            backIconClicked = backIconClicked,
            favoriteIconClicked = favoriteIconClicked
        )

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            // Image
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = pokeImg,
                    contentDescription = "pokemon img",
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
                )

                // 画像選択ボタン
            }

            // Status

        }
    }
}