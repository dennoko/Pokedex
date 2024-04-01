package com.example.pokedex.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokedex.model.room.MainDB
import com.example.pokedex.model.room.MainEntity
import kotlinx.coroutines.flow.Flow

class MainScLocalSource(
    private val db: MainDB
): PagingSource<Int, MainEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MainEntity> {
        return try {
            val page = params.key ?: 1
            val data = db.mainDao().getMainEntity(page)

            LoadResult.Page(
                data = listOf(data),
                prevKey = null, // 1ページ目の前にはページがないので nullb
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MainEntity>): Int? {
        TODO("Not yet implemented")
    }
}