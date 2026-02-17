package com.myapp.duelvault.home.domain

import com.myapp.duelvault.home.presentation.mapper.mapToCardEntity
import com.myapp.duelvault.home.presentation.model.DataCard
import com.myapp.duelvault.utils.Message
import com.myapp.duelvault.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val repo: HomeRepo
) {
    suspend fun getCards(limit: Int): Flow<Resource<List<DataCard>>> {
        return repo.getCards(limit = limit)
            .map { cards ->
                val data = cards.mapToCardEntity()
                Resource.Success(data) as Resource<List<DataCard>>
            }
            .onStart { emit(Resource.Loading()) }
            .catch { e -> emit(Resource.Failure(Message(data = e.message.toString(),code = 400))) }
    }

    suspend fun updateCards(limit: Int) {
        val currentCards = repo.getCurrentCardsCount()
        if (currentCards < limit) {
            repo.updateCards(limit)
        }
    }

    suspend fun saveFavorite(id: Int) {
        repo.saveFavorite(id)
    }

    suspend fun deleteFavorite(id: Int) {
        repo.deleteFavorite(id)
    }

    suspend fun deleteCards() {
        repo.deleteCards()
    }

}