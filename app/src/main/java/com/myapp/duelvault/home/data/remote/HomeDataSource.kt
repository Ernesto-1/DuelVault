package com.myapp.duelvault.home.data.remote

import com.myapp.duelvault.home.data.remote.model.response.ResponseCardData
import com.myapp.duelvault.home.data.remote.service.YGOApi
import javax.inject.Inject

class HomeDataSource @Inject constructor(private val ygoApi: YGOApi) {

    suspend fun getDataAddress(num: Int, offset: Int): ResponseCardData {
        return ygoApi.getCardsData(num = num, offset = offset)
    }

}