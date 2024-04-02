package com.example.pokedex.view.ui_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
fun TypeCard(
    type: String,
    modifier: Modifier = Modifier
) {
    // change the color of the card based on the type
    val color = when (type) {
        "ほのお" -> Color(0xFFFF4040)
        "みず" -> Color(0xFF4A90DA)
        "くさ" -> Color(0xFF77CC55)
        "でんき" -> Color(0xFFFFD700)
        "エスパー" -> Color(0xFFFF007F)
        "こおり" -> Color(0xFF99FFFF)
        "ドラゴン" -> Color(0xFF7038F8)
        "ゴースト" -> Color(0xFF705898)
        "じめん" -> Color(0xFFE0C068)
        "いわ" -> Color(0xFFB8A038)
        "むし" -> Color(0xFFA8B820)
        "どく" -> Color(0xFFA040A0)
        "かくとう" -> Color(0xFFC03028)
        "ひこう" -> Color(0xFFB1B1FF)
        "あく" -> Color(0xFF707070)
        "フェアリー" -> Color(0xFFEE99AC)
        "ノーマル" -> Color(0xFFB0B0B0)
        "はがね" -> Color(0xFFB8B8D0)
        else -> Color.White
    }

    // display the type in a card
    OutlinedCard(
        modifier = Modifier
            .width(80.dp)
            .then(modifier),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.outlinedCardColors(containerColor = color)
    ) {
        Text(
            text = type,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun TypeCardPreview() {
    PokedexTheme {
        Surface {
            TypeCard(type = "ドラゴン")
        }
    }
}