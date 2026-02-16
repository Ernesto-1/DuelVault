package com.myapp.duelvault.home.presentation

sealed class HomeEvent {

    data class SaveFavorite(val id: Int) : HomeEvent()

    data class DeleteFavorite(val id: Int) : HomeEvent()

    object ChargingMoreCards : HomeEvent()


}