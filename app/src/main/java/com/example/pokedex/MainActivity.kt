package com.example.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.model.translation.TranslationManager
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.view.screens.FavoritePokemonSc
import com.example.pokedex.view.screens.MainScreen
import com.example.pokedex.view.ui_components.CustomBottomAppBar
import com.example.pokedex.view.ui_components.CustomTopAppBar
import com.example.pokedex.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainvm: AppViewModel by viewModels()
        // Roomの初期化
        mainvm.initRoomRepository(this)

        // 翻訳の準備
        TranslationManager.loadTranslations(this)

        setContent {
            val navController = rememberNavController()
            // MainScreen UI State
            val mainUiState by mainvm.mainScUiState.collectAsState()
            // FavoritePokemonSc UI State
            val favoritePokemonScUiState by mainvm.favoriteScUiState.collectAsState()

            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            CustomTopAppBar()
                        },
                        bottomBar = {
                            CustomBottomAppBar(
                                homeIconClicked = {
                                    mainvm.changeShowDetail(isShow = false)
                                    navController.navigate("main")
                                    mainvm.clearDetailData()
                                },
                                starIconClicked = {
                                    navController.navigate("favorite")
                                    mainvm.clearDetailData()
                                },
                            )
                        }
                    ) {
                        NavHost(navController, "main", modifier = Modifier.padding(it)) {
                            composable("main") {
                                MainScreen(
                                    mainUiState,
                                    changeShowDetail = {
                                        mainvm.changeShowDetail()
                                    },
                                    changeShowAndInitDetail = { id, urls ->
                                        mainvm.changeShowDetail()
                                        mainvm.getFlavorText(id)
                                        mainvm.getAbility(urls)
                                        mainvm.clearDetailData()
                                    },
                                    onDone = {
                                        mainvm.onDoneAtSearch()
                                    },
                                    pokemonInfoCardClicked = {
                                        mainvm.pokemonInfoCardClicked(it)
                                    },
                                    favoriteIconClicked = {
                                        mainvm.favoriteIconClicked(it)
                                    },
                                    pokeDetailScInit = {
                                        mainvm.isFavorite(it)
                                    }
                                )
                            }

                            composable("favorite") {
                                FavoritePokemonSc(
                                    uiState = favoritePokemonScUiState,
                                    init = {
                                        mainvm.getAllFavoritePokemon()
                                    },
                                    infoCardClicked = {
                                        mainUiState.searchText.value = it.toString()
                                        mainvm.searchPokemon()
                                        mainvm.changeShowDetail(true)
                                        navController.navigate("main")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
