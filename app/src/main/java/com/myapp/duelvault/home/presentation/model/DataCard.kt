package com.myapp.duelvault.home.presentation.model

data class DataCard(
    val id: Int = -1,
    val name: String = "",
    val type: String = "",
    val frameType: String = "",
    val cardImage: String = "",
    val cardPrice: String = "",
    val isFavorite: Boolean = false
)