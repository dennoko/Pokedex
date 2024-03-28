package com.example.pokedex.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pokedex.view.ui_components.SearchBox

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
    ) {
        SearchBox()
    }
}