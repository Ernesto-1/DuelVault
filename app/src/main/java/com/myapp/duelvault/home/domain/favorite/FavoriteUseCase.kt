package com.myapp.duelvault.home.domain.favorite

import com.myapp.duelvault.home.domain.HomeRepo
import com.myapp.duelvault.home.presentation.mapper.DataCard
import com.myapp.duelvault.home.presentation.mapper.mapToCardEntity
import com.myapp.duelvault.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val repo: HomeRepo
) {

    suspend fun getCards(limit: Int): Flow<Resource<List<DataCard>>> {
        return repo.getCardsFavorite(limit = limit)
            .map { cards ->
                val data = cards.mapToCardEntity()
                Resource.Success(data) as Resource<List<DataCard>> }
            .onStart { emit(Resource.Loading()) }
            .catch { e -> emit(Resource.Failure()) }
    }


    suspend fun saveFavorite(id: Int) {
        repo.saveFavorite(id)
    }

    suspend fun deleteFavorite(id: Int) {
        repo.deleteFavorite(id)
    }

}