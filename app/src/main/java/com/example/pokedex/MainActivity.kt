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
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.view.screens.MainScreen
import com.example.pokedex.view.ui_components.CustomTopAppBar
import com.example.pokedex.viewmodel.MainScViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainvm: MainScViewModel by viewModels()
        mainvm.apiTest3()

        setContent {
            val navController = rememberNavController()

            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            CustomTopAppBar()
                        }
                    ) {
                        NavHost(navController, "main", modifier = Modifier.padding(it)) {
                            composable("main") {
                                MainScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
