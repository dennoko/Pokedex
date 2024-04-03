package com.example.pokedex.view.ui_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedex.model.translation.TranslationManager

@Composable
fun DetailItem(
    title: String,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$title:",
            modifier = Modifier
                .width(80.dp),
            style = MaterialTheme.typography.labelLarge,
        )

        content()
    }
}