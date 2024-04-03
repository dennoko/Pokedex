package com.example.pokedex.model.repository

import android.util.Log
import com.example.pokedex.model.data.api_response.AbilityData
import com.example.pokedex.model.data.api_response.FlavorText
import com.example.pokedex.model.data.api_response.NormalApiResponse
import com.example.pokedex.model.data.api_response.PokemonData
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class ApiRepository(
    client: HttpClient
) {
    private val client = client

    // get the pokemon from the id
    suspend fun getPokemonFromId(id: Int): PokemonData {
        Log.d("ApiRepositoryMethod", "getPokemon: $id")
        val response: HttpResponse = client.get("https://pokeapi.co/api/v2/pokemon/$id")
        // Deserialize the response
        val data = response.receive<PokemonData>()
        Log.d("ApiRepositoryMethod", "getPokemon: $data")
        return data
    }

    // get the pokemon from the name
    suspend fun getPokemonFromName(name: String): PokemonData {
        Log.d("ApiRepositoryMethod", "getPokemon: $name")
        val response: HttpResponse = client.get("https://pokeapi.co/api/v2/pokemon/$name")
        // Deserialize the response
        val data = response.receive<PokemonData>()
        Log.d("ApiRepositoryMethod", "getPokemon: $data")
        return data
    }

    // get flavor text
    suspend fun getFlavorText(id: Int): FlavorText {
        val response: HttpResponse = client.get("https://pokeapi.co/api/v2/pokemon-species/$id/")
        // Deserialize the response
        var data = response.receive<FlavorText>()
        data = data.copy(flavorTextEntries = data.flavorTextEntries.filter { it.language.name == "ja" })
        return data
    }

    // get the ability
    suspend fun getAbility(url: String): AbilityData {
        val response: HttpResponse = client.get(url)
        // Deserialize the response
        return response.receive<AbilityData>()
    }
}