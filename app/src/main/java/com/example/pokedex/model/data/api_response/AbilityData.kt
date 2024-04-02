package com.example.pokedex.model.data.api_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityData(
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>,
    val names: List<Name>,
)

@Serializable
data class FlavorTextEntry(
    @SerialName("flavor_text") val flavorText: String, // 特性の説明
    val language: NameAndURL,
    @SerialName("version_group") val versionGroup: NameAndURL, // name はゲームの世代
)

@Serializable
data class Name(
    val language: NameAndURL,
    val name: String, // 特性名
)
