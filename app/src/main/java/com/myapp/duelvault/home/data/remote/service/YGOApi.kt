package com.myapp.duelvault.home.data.remote.service

import com.myapp.duelvault.home.data.remote.model.response.ResponseCardData
import retrofit2.http.GET
import retrofit2.http.Query

interface YGOApi {
    @GET("cardinfo.php")
    suspend fun getCardsData(
        @Query("num") num: Int = 20,
        @Query("offset") offset: Int = 0
    ): ResponseCardData
}