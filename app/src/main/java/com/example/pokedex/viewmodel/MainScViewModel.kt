package com.example.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokedex.model.data.api_response.PokemonData
import com.example.pokedex.model.paging.MainScPagingSource
import com.example.pokedex.model.repository.ApiRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.flow.Flow
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



    // Create a flow of PokemonData
    val myDataFlow: Flow<PagingData<List<PokemonData>>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MainScPagingSource(client) }
    ).flow
}