package com.example.pokedex.model.data.translation

import kotlinx.serialization.Serializable

@Serializable
data class PokeNameTranslation(
    val nameList: List<EnJp>
)

@Serializable
data class EnJp(
    val en: String,
    val jp: String
)
