package com.myapp.duelvault.home.presentation.detail

import com.myapp.duelvault.home.presentation.favorite.FavoriteEvent

sealed class DetailEvent {

    data class GetCards(val id: Int) : DetailEvent()

    data class SaveFavorite(val id: Int) : DetailEvent()

    data class DeleteFavorite(val id: Int) : DetailEvent()

}