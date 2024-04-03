package com.example.pokedex.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokedex.model.data.api_response.PokemonData
import com.example.pokedex.model.translation.TranslationManager
import com.example.pokedex.view.ui_components.PokemonInfoCard
import com.example.pokedex.view.ui_components.SearchBox
import com.example.pokedex.viewmodel.data.MainScUiState
import com.example.pokedex.viewmodel.data.PokeDetailScUiState
import com.example.pokedex.viewmodel.data.PokemonDataForInfoCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(
    uiState: MainScUiState,
    onDone: () -> Unit,
    pokemonInfoCardClicked: (data: PokemonData) -> Unit,
    changeShowDetail: () -> Unit,
    changeShowAndInitDetail: (id: Int?, urls: List<String>) -> Unit,
    favoriteIconClicked: (data: PokemonDataForInfoCard) -> Unit,
    pokeDetailScInit: (id: Int) -> Unit
) {
    val lazyPagingItems = uiState.pokeDataList.collectAsLazyPagingItems()

    Box {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            SearchBox(
                text = uiState.searchText.value,
                onValueChange = {
                    uiState.searchText.value = it
                },
                onDone = {
                    onDone()
                    uiState.searchText.value = it
                    changeShowDetail()
                }
            )

            LazyColumn {
                items(lazyPagingItems.itemCount) { index ->
                    val item = lazyPagingItems[index]
                    // 各 item に対して PokemonInfoCard を生成
                    item?.forEach {
                        val pokeData = PokemonDataForInfoCard(
                            name = TranslationManager.getJPName(it.name),
                            id = it.id,
                            imgUrl = it.sprites.frontDefault ?: "",
                            // Type は TranslationManager.getJPType で日本語に変換
                            types = it.types.map { type -> TranslationManager.getJPType(type.type.name) },
                        )

                        PokemonInfoCard( pokeData ) {
                            pokemonInfoCardClicked(it)
                        }
                    }
                }
            }
        }

        if(uiState.isShowDetail) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                PokeDetailSc(
                    uiState = uiState.pokeDetailScUiState,
                    init = {
                        pokeDetailScInit(it)
                    },
                    backIconClicked = {
                        changeShowAndInitDetail(null, listOf())
                    },
                    favoriteIconClicked = {
                        favoriteIconClicked(it)
                    }
                )
            }
        }
    }


}