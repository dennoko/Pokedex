package com.example.pokedex.view.ui_components

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PokeDetailTopBar(
    isFavorite: Boolean = false,
    idAndName: String = "",
    backIconClicked: () -> Unit = {},
    favoriteIconClicked: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickableIcon(
                imageVector = Icons.Default.ArrowBack,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                contentDescription = "back Icon",
            ) {
                backIconClicked()
            }

            Text(
                text = idAndName,
                modifier = Modifier.weight(5f),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )

            val starColor = if (isFavorite) {
                Color.Yellow
            } else {
                Color.White
            }
            ClickableIcon(
                imageVector = Icons.Default.Star,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                contentDescription = "favorite Icon",
                color = starColor,
            ) {
                favoriteIconClicked()
            }
        }
    }
}