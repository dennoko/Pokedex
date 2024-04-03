package com.example.pokedex.view.ui_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedex.model.data.api_response.Stat

@Composable
fun StatusCard(status: List<Stat>) {
    OutlinedCard(
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            if(status.size == 6) {
                Row {
                    Text(text = "HP:", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                    Text(text = status[0].baseStat.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                    Text(text = "攻撃:", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                    Text(text = status[1].baseStat.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                    Text(text = "防御:", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                    Text(text = status[2].baseStat.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                }

                Row {
                    Text(text = "特攻:", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                    Text(text = status[3].baseStat.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                    Text(text = "特防:", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                    Text(text = status[4].baseStat.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                    Text(text = "素早さ:", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                    Text(text = status[5].baseStat.toString(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium)
                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = "合計:", style = MaterialTheme.typography.labelMedium)
                    Text(text = status.sumBy { it.baseStat }.toString(), style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}