package com.example.pokedex.model.data.api_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonData(
    val abilities: List<Ability>, // 特性
    @SerialName("base_experience") val baseExperience: Int, // 経験値
    val forms: List<NameAndURL>, // フォルム
    // @SerialName("game_indices") val gameIndices: List<GameIndex>, // 登場するゲーム
    val height: Int, // 高さ
    val id: Int, // 図鑑番号
    @SerialName("is_default") val isDefault: Boolean, // デフォルトかどうか
    @SerialName("location_area_encounters") val locationAreaEncounters: String, // 出現場所
    // val moves: List<Move>, // 技
    val name: String, // 名前
    val order: Int, // 図鑑番号
    val species: NameAndURL, // 種族
    val sprites: Sprites, // 画像
    val stats: List<Stat>, // 種族値
    val types: List<Type>, // タイプ
)

@Serializable
data class Ability(
    val ability: NameAndURL, // 特性名
    @SerialName("is_hidden") val isHidden: Boolean, // 隠れ特性かどうか
    val slot: Int, // 特性のスロット
)

@Serializable
data class GameIndex(
    @SerialName("game_index") val gameIndex: Int,
    val version: NameAndURL,
)

@Serializable
data class Move(
    val move: NameAndURL,
    @SerialName("version_group_details") val versionGroupDetails: List<VersionGroupDetail>,
)

@Serializable
data class VersionGroupDetail(
    @SerialName("level_learned_at") val levelLearnedAt: Int,
    @SerialName("move_learn_method") val moveLearnMethod: NameAndURL,
    @SerialName("version_group") val versionGroup: NameAndURL,
)

@Serializable
data class Sprites(
    @SerialName("back_default") val backDefault: String?,
    @SerialName("back_female") val backFemale: String?,
    @SerialName("back_shiny") val backShiny: String?,
    @SerialName("back_shiny_female") val backShinyFemale: String?,
    @SerialName("front_default") val frontDefault: String?,
    @SerialName("front_female") val frontFemale: String?,
    @SerialName("front_shiny") val frontShiny: String?,
    @SerialName("front_shiny_female") val frontShinyFemale: String?,
    val other: Other,
)

@Serializable
data class Other(
    @SerialName("dream_world") val dreamWorld: DreamWorld,
    @SerialName("official-artwork") val officialArtwork: OfficialArtwork,
    val showdown: Showdown,
)

@Serializable
data class DreamWorld(
    @SerialName("front_default") val frontDefault: String?,
    @SerialName("front_female") val frontFemale: String?,
)

@Serializable
data class OfficialArtwork(
    @SerialName("front_default") val frontDefault: String?,
    @SerialName("front_shiny") val frontShiny: String?,
)

@Serializable
data class Showdown(
    @SerialName("back_default") val backDefault: String?,
    @SerialName("back_female") val backFemale: String?,
    @SerialName("back_shiny") val backShiny: String?,
    @SerialName("back_shiny_female") val backShinyFemale: String?,
    @SerialName("front_default") val frontDefault: String?,
    @SerialName("front_female") val frontFemale: String?,
    @SerialName("front_shiny") val frontShiny: String?,
    @SerialName("front_shiny_female") val frontShinyFemale: String?,
)

@Serializable
data class Stat(
    @SerialName("base_stat") val baseStat: Int,
    val effort: Int,
    val stat: NameAndURL,
)

@Serializable
data class Type(
    val slot: Int,
    val type: NameAndURL,
)
