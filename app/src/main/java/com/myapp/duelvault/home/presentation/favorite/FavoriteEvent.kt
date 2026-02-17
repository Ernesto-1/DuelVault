package com.myapp.duelvault.home.presentation.favorite

sealed class FavoriteEvent {
    data class SaveFavorite(val id: Int) : FavoriteEvent()
    data class DeleteFavorite(val id: Int) : FavoriteEvent()
    object ChargingMoreCards : FavoriteEvent()

}