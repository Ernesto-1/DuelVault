package com.myapp.duelvault.home.presentation.favorite

import com.myapp.duelvault.home.presentation.mapper.DataCard

data class FavoriteState(
    val isLoading: Boolean = false,
    val cards: List<DataCard> = emptyList(),
    val error: String? = null
)
