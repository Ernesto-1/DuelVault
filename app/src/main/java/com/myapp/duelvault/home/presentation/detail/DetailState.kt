package com.myapp.duelvault.home.presentation.detail

import com.myapp.duelvault.home.presentation.model.DataDetail

data class DetailState(
    val isLoading: Boolean = false,
    val card: DataDetail = DataDetail(),
)
