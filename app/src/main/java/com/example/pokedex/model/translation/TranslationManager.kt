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

    fun getJPName(name: String): String {
        // enName の頭を大文字に変換
        val enName = name.replaceFirst(name[0], name[0].toUpperCase())
        var result = translations?.nameList?.find { it.en == enName }?.jp
        if (result == null) {
            // enName に - が含まれている場合、- の前半を取得
            val hyphenIndex = enName.indexOf("-")
            if (hyphenIndex != -1) {
                val editedEnName = enName.substring(0, hyphenIndex)
                Log.d("TranslationManagerTest", "editedEnName: $editedEnName")
                result = translations?.nameList?.find { it.en == editedEnName }?.jp
                Log.d("TranslationManagerTest", "result: $result")
            }
        }
        return result ?: enName
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