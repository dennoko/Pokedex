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
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText

@OptIn(ExperimentalPagingApi::class)
class MainScRemoteMediator(
    private val client: HttpClient,
    private val database: MainDB
): RemoteMediator<Int, List<PokemonData>>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, List<PokemonData>>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    // 次のページは現在のページ + 1
                    val listSize = lastItem?.size ?: 0
                    listSize + 1
                }
            }

            // APIからデータを取得
            val response: HttpResponse = client.get("https://pokeapi.co/api/v2/pokemon/${page}")
            // レスポンスを Json のまま String に変換
            val responseString = response.readText()

            // トランザクションを使用してデータベースにデータを保存
            database.withTransaction {
                database.mainDao().insert(MainEntity(state.lastItemOrNull()?.size?:1, responseString))
            }

            MediatorResult.Success(endOfPaginationReached = false)
        } catch (e: Exception) {
            Log.d("MainScRemoteMediatorTest", "Error: \n${e.cause}\n${e.message}")
            MediatorResult.Error(e)
        }
    }
}