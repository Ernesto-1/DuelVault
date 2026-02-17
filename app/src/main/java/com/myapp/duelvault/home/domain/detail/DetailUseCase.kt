package com.myapp.duelvault.home.domain.detail

import com.myapp.duelvault.home.domain.HomeRepo
import com.myapp.duelvault.home.presentation.mapper.DataCard
import com.myapp.duelvault.home.presentation.mapper.DataDetail
import com.myapp.duelvault.home.presentation.mapper.mapDetailCard
import com.myapp.duelvault.home.presentation.mapper.mapListCard
import com.myapp.duelvault.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class DetailUseCase @Inject constructor(
    private val repo: HomeRepo
) {

    suspend fun getCard(id: Int): Flow<Resource<DataDetail>> {
        return repo.getCard(id)
            .map {
                val data = it?.mapDetailCard()
                Resource.Success(data) as Resource<DataDetail>
            }
            .onStart { emit(Resource.Loading()) }
            .catch { emit(Resource.Failure()) }
    }


    suspend fun saveFavorite(id: Int) {
        repo.saveFavorite(id)
    }

    suspend fun deleteFavorite(id: Int) {
        repo.deleteFavorite(id)
    }

}