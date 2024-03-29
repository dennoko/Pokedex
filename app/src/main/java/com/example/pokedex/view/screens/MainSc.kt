package com.example.pokedex.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokedex.model.data.api_response.PokemonData
import com.example.pokedex.view.ui_components.PokemonInfoCard
import com.example.pokedex.view.ui_components.SearchBox
import com.example.pokedex.viewmodel.data.PokemonDataForInfoCard
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(
    data: Flow<PagingData<List<PokemonData>>>
) {
    val lazyPagingItems = data.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        SearchBox()

        LazyColumn {
            items(lazyPagingItems.itemCount) { index ->
                val item = lazyPagingItems[index]
                // 各 item に対して PokemonInfoCard を生成
                item?.forEach {
                    val pokeData = PokemonDataForInfoCard(
                        name = it.name,
                        id = it.id,
                        imgUrl = it.sprites.frontDefault ?: "",
                        types = it.types.map { type -> type.type.name }
                    )

                    PokemonInfoCard(pokeData)
                }
            }
        }
    }
}