package com.example.pokedex.view.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex.R
import com.example.pokedex.ui.theme.PokedexTheme

@Composable
fun CustomBottomAppBar(
    homeIconClicked: () -> Unit = {},
    teamIconClicked: () -> Unit = {},
    starIconClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent),
            horizontalArrangement = Arrangement.Start
        ) {
            Card(
                modifier = Modifier
                    .size(height = 112.dp, width = 96.dp)
                    .background(color = Color.Transparent),
                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 48.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
            ) {}
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent),
            horizontalArrangement = Arrangement.End
        ) {
            Card(
                modifier = Modifier
                    .size(height = 112.dp, width = 96.dp)
                    .background(color = Color.Transparent),
                shape = RoundedCornerShape(topStart = 48.dp, topEnd = 0.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
            ) {}
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .height(96.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                ) {}


                Card(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                ) {
                    Row(
                        Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ClickableIcon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home icon",
                        ) {
                            homeIconClicked()
                        }

                        ClickableIcon(
                            painter = painterResource(R.drawable.groups_icon),
                            contentDescription = "Person icon",
                        ) {
                            teamIconClicked()
                        }

                        ClickableIcon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star icon",
                        ) {
                            starIconClicked()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomBottomAppBarPreview() {
    PokedexTheme {
        Surface {
            CustomBottomAppBar()
        }
    }
}
