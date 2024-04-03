package com.example.pokedex.model.data.api_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlavorText(
    @SerialName("flavor_text_entries") val flavorTextEntries: List<Flavor>,
)

@Serializable
data class Flavor(
    @SerialName("flavor_text") val flavorText: String, // 特性の説明
    val language: NameAndURL,
)