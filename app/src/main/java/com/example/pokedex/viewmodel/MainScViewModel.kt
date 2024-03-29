package com.example.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.repository.ApiRepository
import com.example.pokedex.viewmodel.data.PokemonDataForInfoCard
import kotlinx.coroutines.launch

class MainScViewModel: ViewModel() {
    val apiRepository = ApiRepository()

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