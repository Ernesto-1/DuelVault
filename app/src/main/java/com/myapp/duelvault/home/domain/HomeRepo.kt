package com.myapp.duelvault.home.domain

import com.myapp.duelvault.home.data.local.entitys.CardWithFavorite
import kotlinx.coroutines.flow.Flow

interface HomeRepo {

    suspend fun getCards(limit: Int): Flow<List<CardWithFavorite>>

    suspend fun getCardsFavorite(limit: Int): Flow<List<CardWithFavorite>>

    suspend fun getCard(id: Int): Flow<CardWithFavorite?>

    suspend fun updateCards(offset: Int)

    suspend fun getCurrentCardsCount(): Int

    suspend fun saveFavorite(
        id: Int
    )

    suspend fun deleteFavorite(
        id: Int
    )

    suspend fun deleteCards()

}