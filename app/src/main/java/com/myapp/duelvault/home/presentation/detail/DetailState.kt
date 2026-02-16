package com.myapp.duelvault.home.presentation.detail

import com.myapp.duelvault.home.presentation.mapper.DataCard

data class DetailState(
    val isLoading: Boolean = false,
    val card: DataCard = DataCard(),
)
