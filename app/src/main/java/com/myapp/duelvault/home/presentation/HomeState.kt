package com.myapp.duelvault.home.presentation

import com.myapp.duelvault.home.presentation.model.DataCard

data class HomeState(
    val isLoading: Boolean = false,
    val cards: List<DataCard> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null
)
