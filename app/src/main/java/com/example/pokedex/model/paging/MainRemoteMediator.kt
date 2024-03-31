package com.example.pokedex.model.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pokedex.model.data.api_response.PokemonData
import com.example.pokedex.model.room.MainDB
import com.example.pokedex.model.room.MainEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

@OptIn(ExperimentalPagingApi::class)
class MainRemoteMediator(
    private val client: HttpClient,
    private val db: MainDB
): RemoteMediator<Int, PokemonData>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, PokemonData>): MediatorResult {
        return try {
            // determine the page number from the LoadType
            val page = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    // return lastItem id + 1
                    val lastPage = lastItem?.id ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastPage + 1
                }
            }

            // get the data from the API
            val response: HttpResponse = client.get("https://pokeapi.co/api/v2/pokemon/${page}")
            // HttpResponse convert to String
            val data = response.receive<String>()

            // insert the data into the database
            db.withTransaction {
                // insert the new data
                db.mainDao().insert(MainEntity(page, data))
            }

            // determine if the end of the pagination is reached
            MediatorResult.Success(endOfPaginationReached = data.isEmpty())
        } catch (e: Exception) {
            Log.d("MainMediatorTest", "Error:\n${e.cause}\n${e.message}")
            MediatorResult.Error(e)
        }
    }
}