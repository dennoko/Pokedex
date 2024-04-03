package com.example.pokedex.view.ui_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableCard(title: String, content: @Composable () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, RoundedCornerShape(16.dp))
            .clickable { isExpanded = !isExpanded },
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column {
            Row {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(6f),
                    style = MaterialTheme.typography.labelLarge
                )

                if (isExpanded) {
                    ClickableIcon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Close",
                        modifier = Modifier.weight(1f),
                        color = Color.Black
                    ) {
                        isExpanded = false
                    }
                } else {
                    ClickableIcon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Open",
                        modifier = Modifier.weight(1f),
                        color = Color.Black
                    ) {
                        isExpanded = true
                    }
                }
            }

            if(isExpanded) {
                Divider(modifier = Modifier.padding(4.dp))

                content()
            }
        }
    }
}