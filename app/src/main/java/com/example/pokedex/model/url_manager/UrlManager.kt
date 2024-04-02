package com.example.pokedex.model.url_manager

object UrlManager {
    fun getNextGifImageUrl(id: Int, currentImageIndex: Int = 0): String {
        val urlList = listOf(
            // front_default
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${id}.gif",
            // back_default
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/back/${id}.gif",
            // front_shiny
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/shiny/${id}.gif",
            // back_shiny
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/back/shiny/${id}.gif"
        )

        val nextIndex = (currentImageIndex + 1) % urlList.size
        return urlList[nextIndex]
    }

    fun getPrevGigImageUrl(id: Int, currentImageIndex: Int = 0): String {
        val urlList = listOf(
            // front_default
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${id}.gif",
            // back_default
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/back/${id}.gif",
            // front_shiny
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/shiny/${id}.gif",
            // back_shiny
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/back/shiny/${id}.gif"
        )

        val prevIndex = (currentImageIndex - 1 + urlList.size) % urlList.size
        return urlList[prevIndex]
    }
}