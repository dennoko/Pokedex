package com.example.pokedex.view.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.viewmodel.data.PokemonDataForInfoCard

@Composable
fun PokemonInfoCard(
    data: PokemonDataForInfoCard,
    infoCardClicked: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .height(120.dp)
                .background(color = MaterialTheme.colorScheme.background)
                .padding(8.dp)
                .clickable { infoCardClicked() },
            colors = CardDefaults.elevatedCardColors( containerColor = Color.White ),
            elevation = CardDefaults.elevatedCardElevation( 8.dp )
        ) {
            // Pokemon Info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = data.imgUrl,
                    contentDescription = "Pokemon image",
                    modifier = Modifier
                        .size(120.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                )

                // Pokemon name, id, and types
                Column {
                    // id and name
                    Row(
                        modifier = Modifier
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "No.${data.id}",
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 8.dp),
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = data.name,
                            modifier = Modifier
                                .weight(3f)
                                .padding(vertical = 8.dp),
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    Divider(color = Color.Gray)

                    // types
                    Row(
                        modifier = Modifier
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (type in data.types) {
                            TypeCard(type)
                            Spacer(Modifier.width(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PokemonInfoCardPreview() {
    // Sample data
    val data = PokemonDataForInfoCard(
        name = "ピカチュウ",
        id = 25,
        imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
        types = listOf("でんき", "あく")
    )

    PokedexTheme {
        Surface {
            PokemonInfoCard(data)
        }
    }
}
