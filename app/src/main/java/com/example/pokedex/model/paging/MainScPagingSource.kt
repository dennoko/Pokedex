package com.example.pokedex.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokedex.model.data.api_response.PokemonData
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainScPagingSource(
    private val client: HttpClient
): PagingSource<Int, List<PokemonData>>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, List<PokemonData>> {
        return try {
            val page = params.key ?: 1
            // データのリストを用意
            val data = mutableListOf<List<PokemonData>>()

            val response: HttpResponse = withContext(Dispatchers.IO) { client.get("https://pokeapi.co/api/v2/pokemon/${page}") }
            val pokemonData = response.receive<PokemonData>()
            data.add(listOf(pokemonData))

            LoadResult.Page(
                data = data,
                prevKey = if(page == 1) null else page - 1,
                nextKey = if(data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, List<PokemonData>>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}