package com.example.pokedex.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokedex.model.data.api_response.AbilityData
import com.example.pokedex.model.data.api_response.PokemonData
import com.example.pokedex.model.paging.MainScPagingSource
import com.example.pokedex.model.repository.ApiRepository
import com.example.pokedex.model.repository.RoomRepository
import com.example.pokedex.model.room.FavoriteDB
import com.example.pokedex.model.translation.TranslationManager
import com.example.pokedex.viewmodel.data.FavoritePokemonScUiState
import com.example.pokedex.viewmodel.data.MainScUiState
import com.example.pokedex.viewmodel.data.PokeDetailScUiState
import com.example.pokedex.viewmodel.data.PokemonDataForInfoCard
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

class AppViewModel: ViewModel() {
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
    private var roomRepository: RoomRepository? = null

    // init the room repository
    fun initRoomRepository(context: Context) {
        val db = FavoriteDB.getInstance(context)
        roomRepository = RoomRepository(db)
    }

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

    private val _favoriteScUiState = MutableStateFlow(
        FavoritePokemonScUiState()
    )
    val favoriteScUiState: StateFlow<FavoritePokemonScUiState> = _favoriteScUiState.asStateFlow()

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
    fun searchPokemon() {
        viewModelScope.launch {
            // if the input is a number, search by id
            try {
                if (_uiState.value.searchText.value.toIntOrNull() != null) {
                    val pokemon = apiRepository.getPokemonFromId(_uiState.value.searchText.value.toInt())
                    Log.d("SearchTest", "searchPokemon by id: $pokemon")
                    _uiState.value.pokeDetailScUiState.isFavorite.value = roomRepository?.checkFavorite(pokemon.id) ?: false
                    setDetailData(pokemon)
                    getAbility(pokemon.abilities.map { it.ability.url })
                    getFlavorText(pokemon.id)
                    _uiState.value.searchText.value = ""
                } else {
                    // if the input is a string, search by name
                    // translate the name to english
                    val enName = TranslationManager.getENName(_uiState.value.searchText.value)
                    val pokemon = apiRepository.getPokemonFromName(enName)
                    Log.d("SearchTest", "searchPokemon by name: $pokemon")
                    _uiState.value.pokeDetailScUiState.isFavorite.value = roomRepository?.checkFavorite(pokemon.id) ?: false
                    setDetailData(pokemon)
                    getAbility(pokemon.abilities.map { it.ability.url })
                    getFlavorText(pokemon.id)
                    _uiState.value.searchText.value = ""
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

    // insert the favorite pokemon
    fun insertFavoritePokemon(pokemonDataForInfoCard: PokemonDataForInfoCard) {
        roomRepository?.let {
            viewModelScope.launch {
                it.insertFavoritePokemon(pokemonDataForInfoCard)
            }
        }
    }

    // delete the favorite pokemon
    fun deleteFavoritePokemon(id: Int) {
        _uiState.value.pokeDetailScUiState.isFavorite.value = false
        roomRepository?.let {
            viewModelScope.launch {
                it.deleteFavoritePokemon(id)
            }
        }
    }

    // get all favorite pokemon
    fun getAllFavoritePokemon() {
        roomRepository?.let {
            viewModelScope.launch {
                val newFavoriteScUiState = _favoriteScUiState.value.copy(favoritePokemonList = it.getAllFavoritePokemon())
                _favoriteScUiState.value = newFavoriteScUiState
            }
        }
    }

    // check the pokemon is favorite
    fun isFavorite(id: Int) {
        viewModelScope.launch {
            roomRepository?.let {
                val newUiState = _uiState.value.copy(
                    pokeDetailScUiState = _uiState.value.pokeDetailScUiState.copy(
                        isFavorite = mutableStateOf(it.checkFavorite(id))
                    )
                )
                _uiState.value = newUiState
                Log.d("isFavoriteMethod", "id: $id  isFavorite: ${_uiState.value.pokeDetailScUiState.isFavorite.value}")
            }
        }
    }

    fun favoriteIconClicked(data: PokemonDataForInfoCard) {
        if(_uiState.value.pokeDetailScUiState.isFavorite.value) {
            _uiState.value.pokeDetailScUiState.data?.let { deleteFavoritePokemon(it.id) }
        } else {
            // if the pokemon is not favorite, insert it
            _uiState.value.pokeDetailScUiState.isFavorite.value = true
            _uiState.value.pokeDetailScUiState.data?.let {
                insertFavoritePokemon(data)
            }
        }
    }
}