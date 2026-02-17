package com.myapp.duelvault.utils.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    object Onboarding : AppDestination

    @Serializable
    object Splash : AppDestination

    @Serializable
    object Welcome : AppDestination


    @Serializable
    object DashboardGraph : AppDestination

    @Serializable
    object Home : AppDestination
    @Serializable
    data class Detail(val id: Int) : AppDestination
    @Serializable
    object Favorite : AppDestination

}