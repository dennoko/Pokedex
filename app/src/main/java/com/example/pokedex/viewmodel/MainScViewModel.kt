package com.example.pokedex.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokedex.model.data.api_response.PokemonData
import com.example.pokedex.model.paging.MainRemoteMediator
import com.example.pokedex.model.paging.MainScLocalSource
import com.example.pokedex.model.paging.MainScPagingSource
import com.example.pokedex.model.repository.ApiRepository
import com.example.pokedex.model.room.MainDB
import com.example.pokedex.model.room.MainEntity
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private var db: MainDB? = null
    fun initDB(context: Context) { db = MainDB.getDB(context) }

    // Create a flow of PokemonData
    val myDataFlow: Flow<PagingData<List<PokemonData>>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MainScPagingSource(client) }
    ).flow

    @OptIn(ExperimentalPagingApi::class)
    val myDataFlowFromRemoteMediator: Flow<PagingData<MainEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        remoteMediator = MainRemoteMediator(client, db!!),
        pagingSourceFactory = { MainScLocalSource(db!!) }
    ).flow
}