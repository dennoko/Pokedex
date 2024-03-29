package com.example.pokedex.model.translation

import com.example.pokedex.model.data.translation.PokeNameTranslation
import android.content.Context
import android.util.Log
import kotlinx.serialization.json.Json

object TranslationManager {
    private var translations: PokeNameTranslation?= null

    fun loadTranslations(context: Context) {
        if(translations == null) {
            try {
                context.assets.open("name.json").bufferedReader().use { reader ->
                    val jsonString = reader.readText()
                    // Json データを kotlin-serialization で PokeNameTranslation オブジェクトに変換
                    translations = Json.decodeFromString(PokeNameTranslation.serializer(), jsonString)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("TranslationManagerTest", "Failed to load translations\n${e.cause}\n${e.message}")
            }
        }
    }

    fun getJPName(enName: String): String {
        // enName の頭を大文字に変換
        val enName = enName.replaceFirst(enName[0], enName[0].toUpperCase())
        return translations?.nameList?.find { it.en == enName }?.jp ?: enName
    }

    fun getENName(jaName: String): String {
        val enName = translations?.nameList?.find { it.jp == jaName }?.en ?: jaName
        // enName の頭を小文字に変換
        return enName.replaceFirst(enName[0], enName[0].toLowerCase())
    }
    // Type Translation
    fun getJPType(enType: String): String {
        // Switch the type name to the Japanese name
        return when(enType) {
            "normal" -> "ノーマル"
            "fighting" -> "かくとう"
            "flying" -> "ひこう"
            "poison" -> "どく"
            "ground" -> "じめん"
            "rock" -> "いわ"
            "bug" -> "むし"
            "ghost" -> "ゴースト"
            "steel" -> "はがね"
            "fire" -> "ほのお"
            "water" -> "みず"
            "grass" -> "くさ"
            "electric" -> "でんき"
            "psychic" -> "エスパー"
            "ice" -> "こおり"
            "dragon" -> "ドラゴン"
            "dark" -> "あく"
            "fairy" -> "フェアリー"
            else -> enType
        }
    }
}