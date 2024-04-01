package com.example.pokedex.view.ui_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ClickableIcon(
    imageVector: ImageVector? = null,
    painter: Painter? = null,
    modifier: Modifier = Modifier,
    contentDescription: String,
    size: Dp = 32.dp,
    color: Color = Color.White,
    iconClicked: () -> Unit
) {
    imageVector?.let {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier
                .clickable(
                    onClick = { iconClicked() },
                    indication = rememberRipple( bounded = false ),
                    interactionSource = remember { MutableInteractionSource() }
                )
                .aspectRatio(1f)
                .size(size)
                .then(modifier),
            tint = color
        )
    }

    painter?.let {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .clickable(
                    onClick = { iconClicked() },
                    indication = rememberRipple( bounded = false ),
                    interactionSource = remember { MutableInteractionSource() }
                )
                .aspectRatio(1f)
                .size(size)
                .then(modifier),
            tint = color
        )
    }
}