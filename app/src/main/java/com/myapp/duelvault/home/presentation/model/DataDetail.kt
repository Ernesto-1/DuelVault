package com.myapp.duelvault.home.presentation.model

data class DataDetail(
    val id: Int = -1,
    val name: String = "",
    val type: String = "",
    val frameType: String = "",
    val cardImage: String = "",
    val cardPrice: String = "",
    val isFavorite: Boolean = false,
    val desc: String = "",
    val atk: Int = -1,
    val def: Int = -1,
    val level: Int = -1,
    val race: String = "",
    val attribute: String = "",
    val cardImages: String = "",
    val cardPrices: String = ""
)