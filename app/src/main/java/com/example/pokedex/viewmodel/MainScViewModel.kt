package com.example.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.repository.ApiRepository
import com.example.pokedex.viewmodel.data.PokemonDataForInfoCard
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainScViewModel: ViewModel() {
    // Create a Ktor client
    private val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    ignoreUnknownKeys = true // if the server sends extra fields, ignore them
                }
            )
        }
    }

    private val apiRepository = ApiRepository(client)

    fun apiTest() {
        Log.d("MainViewModelMethod", "apiTest()")
        viewModelScope.launch { apiRepository.getPokemonList() }
    }

    fun apiTest2() {
        Log.d("MainViewModelMethod", "apiTest2()")
        // random number between 1 and 100
        viewModelScope.launch { apiRepository.getPokemonFromId((1..100).random()) }
    }

    fun apiTest3() {
        Log.d("MainViewModelMethod", "apiTest3()")
        viewModelScope.launch { apiRepository.getPokemonFromName("pikachu") }
    }

    fun uiTest(): List<PokemonDataForInfoCard> {
        val returnList = mutableListOf<PokemonDataForInfoCard>()

        val data = PokemonDataForInfoCard(
            name = "ピカチュウ",
            id = 25,
            imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
            types = listOf("でんき", "あく")
        )

        for(i in 1..100) {
            returnList.add(data)
        }

        return returnList
    }
}