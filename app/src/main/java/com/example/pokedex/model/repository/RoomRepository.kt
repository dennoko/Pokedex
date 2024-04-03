package com.example.pokedex.model.repository

import com.example.pokedex.model.room.FavoriteDB
import com.example.pokedex.model.room.FavoriteEntity
import com.example.pokedex.model.translation.TranslationManager
import com.example.pokedex.viewmodel.data.PokemonDataForInfoCard

class RoomRepository(db: FavoriteDB) {
    private val favoriteDao = db.favoriteDao()
    // insert favorite pokemon
    suspend fun insertFavoritePokemon(pokemonDataForInfoCard: PokemonDataForInfoCard) {
        val type1 = pokemonDataForInfoCard.types[0]
        val type2 = if (pokemonDataForInfoCard.types.size > 1) {
            pokemonDataForInfoCard.types[1]
        } else {
            null
        }

        // check id is already in favorite
        val isExist = favoriteDao.isExist(pokemonDataForInfoCard.id)

        if (isExist) {
            return
        } else {
            favoriteDao.insert(
                FavoriteEntity(
                    id = pokemonDataForInfoCard.id,
                    name = TranslationManager.getJPName(pokemonDataForInfoCard.name),
                    imgUrl = pokemonDataForInfoCard.imgUrl,
                    type1 = type1,
                    type2 = type2
                )
            )
        }
    }

    // delete favorite pokemon
    suspend fun deleteFavoritePokemon(id: Int) {
        favoriteDao.delete(id)
    }

    // get all favorite pokemon
    suspend fun getAllFavoritePokemon(): List<PokemonDataForInfoCard> {
        return favoriteDao.getAll().map {
            PokemonDataForInfoCard(
                name = it.name,
                id = it.id,
                imgUrl = it.imgUrl,
                types = if (it.type2 == null) {
                    listOf(it.type1)
                } else {
                    listOf(it.type1, it.type2)
                }
            )
        }
    }

    suspend fun checkFavorite(id: Int): Boolean {
        val favoriteList = favoriteDao.getAllId()
        return favoriteList.contains(id)
    }
}