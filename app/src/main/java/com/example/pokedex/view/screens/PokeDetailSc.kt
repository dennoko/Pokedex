package com.example.pokedex.view.screens

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size.Companion.ORIGINAL
import com.example.pokedex.model.data.api_response.AbilityData
import com.example.pokedex.model.data.api_response.PokemonData
import com.example.pokedex.model.translation.TranslationManager
import com.example.pokedex.model.url_manager.UrlManager
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.view.ui_components.ClickableIcon
import com.example.pokedex.view.ui_components.PokeDetailGif
import com.example.pokedex.view.ui_components.PokeDetailTopBar
import com.example.pokedex.view.ui_components.TypeCard
import io.ktor.http.ContentType.Image.GIF

@Composable
fun PokeDetailSc(
    data: PokemonData?,
    isFavorite: Boolean = false,
    abilities: List<AbilityData> = listOf(),
    backIconClicked: () -> Unit = {},
    favoriteIconClicked: () -> Unit = {},
) {
    Log.d("DataTest", "${data?.sprites?.other?.showdown}")

    Column(
        modifier = Modifier
    ) {
        PokeDetailTopBar(
            isFavorite = isFavorite,
            idAndName = "No.${data?.id}  ${TranslationManager.getJPName(data?.name!!)}",
            backIconClicked = backIconClicked,
            favoriteIconClicked = favoriteIconClicked
        )

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            // Image
            PokeDetailGif(data = data)

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.secondary)

            // Status
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // types
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "タイプ： ",
                        modifier = Modifier
                            .width(80.dp),
                        style = MaterialTheme.typography.labelLarge,
                    )

                    for (type in data.types) {
                        TypeCard(
                            type = TranslationManager.getJPType(type.type.name),
                        )
                        Spacer(Modifier.width(8.dp))
                    }
                }

                // abilities
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "特性： ",
                        modifier = Modifier
                            .width(80.dp),
                        style = MaterialTheme.typography.labelLarge,
                    )

                    Column {
                        for (ability in abilities) {
                            var abilityNameAtJP = ""
                            ability.names.forEach {
                                if (it.language.name == "ja") {
                                    abilityNameAtJP = it.name
                                    return@forEach
                                }
                            }
                            Text(
                                text = abilityNameAtJP,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}