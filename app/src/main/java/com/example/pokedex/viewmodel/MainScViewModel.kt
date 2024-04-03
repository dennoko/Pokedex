package com.example.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokedex.model.data.api_response.AbilityData
import com.example.pokedex.model.data.api_response.FlavorText
import com.example.pokedex.model.data.api_response.PokemonData
import com.example.pokedex.model.paging.MainScPagingSource
import com.example.pokedex.model.repository.ApiRepository
import com.example.pokedex.model.translation.TranslationManager
import com.example.pokedex.viewmodel.data.MainScUiState
import com.example.pokedex.viewmodel.data.PokeDetailScUiState
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    // Create a flow of PokemonData
    val myDataFlow: Flow<PagingData<List<PokemonData>>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MainScPagingSource(client) }
    ).flow

    private val _uiState = MutableStateFlow(
        MainScUiState(
            pokeDataList = myDataFlow,
        )
    )
    val mainScUiState: StateFlow<MainScUiState> = _uiState.asStateFlow()

    fun changeShowDetail(isShow: Boolean = !_uiState.value.isShowDetail) {
        val newUiState = _uiState.value.copy(isShowDetail = isShow)
        _uiState.value = newUiState
    }

    // get the flavor text
    fun getFlavorText(id: Int?) {
        if (id != null) {
            viewModelScope.launch {
                try {
                    val flavorText = apiRepository.getFlavorText(id)
                    val newDetailState = _uiState.value.pokeDetailScUiState.copy(flavorText = flavorText.flavorTextEntries[0].flavorText)
                    val newUiState = _uiState.value.copy(pokeDetailScUiState = newDetailState)
                    _uiState.value = newUiState
                    Log.d("MainScViewModelMethod", "getFlavorText: $flavorText")
                } catch (e: Exception) {
                    Log.d("MainScViewModelMethod", "getFlavorText: \n${e.cause}\n${e.message}")
                }
            }
        }
    }

    // get the ability
    fun getAbility(urls: List<String>) {
        viewModelScope.launch {
            val getAbilities = mutableListOf<AbilityData>()
            urls.forEach { url ->
                val ability = apiRepository.getAbility(url)
                getAbilities.add(ability)
            }

            val newDetailState = _uiState.value.pokeDetailScUiState.copy(abilities = getAbilities)
            val newUiState = _uiState.value.copy(pokeDetailScUiState = newDetailState)
            _uiState.value = newUiState
            Log.d("MainScViewModelMethod", "getAbility: $getAbilities")
        }
    }

    // search the pokemon
    private fun searchPokemon() {
        viewModelScope.launch {
            // if the input is a number, search by id
            try {
                if (_uiState.value.searchText.value.toIntOrNull() != null) {
                    val pokemon = apiRepository.getPokemonFromId(_uiState.value.searchText.value.toInt())
                    Log.d("SearchTest", "searchPokemon by id: $pokemon")
                    setDetailData(pokemon)
                    getAbility(pokemon.abilities.map { it.ability.url })
                    getFlavorText(pokemon.id)
                } else {
                    // if the input is a string, search by name
                    // translate the name to english
                    val enName = TranslationManager.getENName(_uiState.value.searchText.value)
                    val pokemon = apiRepository.getPokemonFromName(enName)
                    Log.d("SearchTest", "searchPokemon by name: $pokemon")
                    setDetailData(pokemon)
                    getAbility(pokemon.abilities.map { it.ability.url })
                    getFlavorText(pokemon.id)
                }
            } catch (e: Exception) {
                Log.d("SearchTest", "searchPokemon: \n${e.cause}\n${e.message}")
            }
        }
    }

    // set the detail data
    private fun setDetailData(data: PokemonData) {
        val newDetailState = _uiState.value.pokeDetailScUiState.copy(data = data)
        val newUiState = _uiState.value.copy(pokeDetailScUiState = newDetailState)
        _uiState.value = newUiState
    }

    fun pokemonInfoCardClicked( pokeData: PokemonData ) {
        setDetailData(pokeData)
        getAbility(pokeData.abilities.map { it.ability.url })
        getFlavorText(pokeData.id)
        changeShowDetail()
    }

    fun onDoneAtSearch() {
        searchPokemon()
    }

    // clear the detail data
    fun clearDetailData() {
        val newDetailState = PokeDetailScUiState()
        val newUiState = _uiState.value.copy(pokeDetailScUiState = newDetailState)
        _uiState.value = newUiState
    }
}