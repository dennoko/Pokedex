package com.example.pokedex.view.ui_components

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.pokedex.model.data.api_response.PokemonData
import com.example.pokedex.model.url_manager.UrlManager

@Composable
fun PokeDetailGif(
    id: Int,
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if(Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    var imgData by remember { mutableStateOf(UrlManager.getDefaultImageUrl(id)) }

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(imgData)
            .apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = imageLoader
    )

    var currentImageIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickableIcon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Left triangle icon",
                modifier = Modifier
                    .weight(1f),
                color = Color.Black
            ) {
                imgData = UrlManager.getPrevGigImageUrl(id, currentImageIndex)
                currentImageIndex = (currentImageIndex - 1 + 4) % 4
            }

            Image(
                painter = painter,
                contentDescription = "Pokemon image",
                modifier = Modifier
                    .weight(4f)
                    .size(240.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            )

            ClickableIcon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Right triangle icon",
                modifier = Modifier
                    .weight(1f),
                color = Color.Black
            ) {
                imgData = UrlManager.getNextGifImageUrl(id, currentImageIndex)
                currentImageIndex = (currentImageIndex + 1) % 4
            }
        }
    }
}