package com.myapp.duelvault.home.presentation.detail

sealed class DetailEvent {

    data class GetCards(val id: Int) : DetailEvent()

    data class SaveFavorite(val id: Int) : DetailEvent()

    data class DeleteFavorite(val id: Int) : DetailEvent()

}