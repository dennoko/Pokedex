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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.pokedex.model.data.api_response.FlavorText
import com.example.pokedex.model.data.api_response.PokemonData
import com.example.pokedex.model.translation.TranslationManager
import com.example.pokedex.model.url_manager.UrlManager
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.view.ui_components.ClickableIcon
import com.example.pokedex.view.ui_components.DetailItem
import com.example.pokedex.view.ui_components.ExpandableCard
import com.example.pokedex.view.ui_components.PokeDetailGif
import com.example.pokedex.view.ui_components.PokeDetailTopBar
import com.example.pokedex.view.ui_components.StatusCard
import com.example.pokedex.view.ui_components.TypeCard
import com.example.pokedex.viewmodel.data.PokeDetailScUiState
import com.example.pokedex.viewmodel.data.PokemonDataForInfoCard
import io.ktor.http.ContentType.Image.GIF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun PokeDetailSc(
    uiState: PokeDetailScUiState,
    init: (id: Int) -> Unit,
    backIconClicked: () -> Unit = {},
    favoriteIconClicked: (data: PokemonDataForInfoCard) -> Unit = {},
) {
    Log.d("DataTest", "${uiState.data?.sprites?.other?.showdown}")
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            uiState.data?.let {
                Log.d("initTest", "${it.id}")
                init(it.id)
            }
        }
    }


    Column(
        modifier = Modifier
    ) {
        PokeDetailTopBar(
            isFavorite = uiState.isFavorite.value,
            idAndName = "No.${uiState.data?.id}  ${TranslationManager.getJPName(uiState.data?.name?: "")}",
            backIconClicked = backIconClicked,
            favoriteIconClicked = {
                favoriteIconClicked(
                    PokemonDataForInfoCard(
                        id = uiState.data?.id?: 0,
                        name = uiState.data?.name?: "",
                        imgUrl = uiState.data?.sprites?.frontDefault ?:"",
                        types = listOf(
                            TranslationManager.getJPType(uiState.data?.types?.get(0)?.type?.name?: ""),
                            TranslationManager.getJPType(uiState.data?.types?.getOrNull(1)?.type?.name?: "")
                        ),
                    )
                )
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            // Image
            if(uiState.data?.id != null) {
                PokeDetailGif(id = uiState.data.id)
            }

            Divider(modifier = Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.secondary)

            // Status
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // types
                DetailItem(title = "タイプ") {
                    if ( (uiState.data?.types != null) && uiState.data.types.isNotEmpty()) {
                        for (type in uiState.data.types) {
                            TypeCard(
                                type = TranslationManager.getJPType(type.type.name),
                            )
                            Spacer(Modifier.width(8.dp))
                        }
                    }
                }

                // flavor text
                DetailItem(title = "生態") {
                    val txt = uiState.flavorText
                    val singleLineTxt = txt.replace("\n", "")
                    Text(text = singleLineTxt)
                }

                // abilities
                DetailItem(title = "特性") {
                    Column {
                        if(uiState.abilities.isNotEmpty()){
                            for (ability in uiState.abilities) {
                                var abilityNameAtJP = ""
                                ability.names.forEach {
                                    if (it.language.name == "ja") {
                                        abilityNameAtJP = it.name
                                        return@forEach
                                    }
                                }

                                var abilityDescriptionAtJP = ""
                                ability.flavorTextEntries.forEach {
                                    if (it.language.name == "ja") {
                                        abilityDescriptionAtJP = it.flavorText
                                        abilityDescriptionAtJP = abilityDescriptionAtJP.replace("\n", "")
                                        return@forEach
                                    }
                                }

                                ExpandableCard(
                                    title = abilityNameAtJP,
                                ) {
                                    Text(
                                        text = abilityDescriptionAtJP,
                                        modifier = Modifier
                                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }

                // status
                DetailItem(title = "種族値") {
                    StatusCard(status = uiState.data?.stats?: listOf())
                }
            }
        }
    }
}