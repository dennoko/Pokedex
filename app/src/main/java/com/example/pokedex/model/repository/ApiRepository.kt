package com.example.pokedex.model.repository

import android.util.Log
import com.example.pokedex.model.data.api_response.NormalApiResponse
import com.example.pokedex.model.data.api_response.PokemonData
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class ApiRepository {
    // Create a Ktor client
    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true // if the server sends extra fields, ignore them
                }
            )
        }
    }

    // get the list of all pokemons
    suspend fun getPokemonList(): NormalApiResponse {
        val response: HttpResponse = client.get("https://pokeapi.co/api/v2/pokemon?limit=1000")
        // Deserialize the response
        return response.receive<NormalApiResponse>()
    }

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
}