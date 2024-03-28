package com.example.pokedex.view.ui_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex.ui.theme.PokedexTheme

@Composable
fun CustomTopAppBar(title: String = "Pokedex") {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        border = BorderStroke(2.dp, Color.Black),
        colors = CardDefaults.cardColors( MaterialTheme.colorScheme.primary )
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Preview
@Composable
fun CustomTopAppBarPreview() {
    Surface {
        PokedexTheme {
            CustomTopAppBar()
        }
    }
}