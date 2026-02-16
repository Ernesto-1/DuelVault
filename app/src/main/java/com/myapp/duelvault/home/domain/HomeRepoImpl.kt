package com.myapp.duelvault.home.domain

import android.util.Log
import com.myapp.duelvault.home.data.local.dao.DataCardDao
import com.myapp.duelvault.home.data.local.dao.FavoritesDao
import com.myapp.duelvault.home.data.local.entitys.CardWithFavorite
import com.myapp.duelvault.home.data.remote.HomeDataSource
import com.myapp.duelvault.home.domain.mapper.mapToCardEntity
import com.myapp.duelvault.utils.datastore.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val dataSource: HomeDataSource,
    private val dao: DataCardDao,
    private val daoFavorites: FavoritesDao,
    private val preferencesRepo: PreferencesRepository,
) : HomeRepo {

    override suspend fun getCards(limit: Int): Flow<List<CardWithFavorite>> {
        return dao.getCards(limit = limit)
    }

    override suspend fun getCardsFavorite(limit: Int): Flow<List<CardWithFavorite>> {
        return dao.getCardsFavorite(limit = limit)
    }

    override suspend fun getCard(id: Int): Flow<CardWithFavorite?> {
        return dao.getCard(id)
    }

    override suspend fun updateCards() {
        val offset = preferencesRepo.currentOffset

        try {
            val data = dataSource.getDataAddress(
                num = 20,
                offset = offset.first()
            ).data


            if (data != null) {
                dao.upsertCards(data.mapToCardEntity())
                preferencesRepo.saveOffset(offset.first() + 20)
                Log.d("tfcvygbhnj", preferencesRepo.currentOffset.first().toString())
            }

        } catch (e: Exception) {
            Log.d("tfcvygbhnj7", e.toString())
            throw e
        }
    }

    override suspend fun saveFavorite(id: Int) {
        daoFavorites.saveFavorite(id)
    }

    override suspend fun deleteFavorite(id: Int) {
        daoFavorites.deleteCards(id)
    }

}