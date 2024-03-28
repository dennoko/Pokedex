package com.example.pokedex.model.data.api_response

import kotlinx.serialization.Serializable

@Serializable
data class NormalApiResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<NameAndURL>
)

@Serializable
data class NameAndURL(
    val name: String,
    val url: String
)
