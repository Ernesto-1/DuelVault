package com.myapp.duelvault.home.domain

import com.myapp.duelvault.home.data.local.dao.DataCardDao
import com.myapp.duelvault.home.data.local.dao.FavoritesDao
import com.myapp.duelvault.home.data.local.entitys.CardWithFavorite
import com.myapp.duelvault.home.data.remote.HomeDataSource
import com.myapp.duelvault.home.domain.mapper.mapToCardEntity
import com.myapp.duelvault.utils.datastore.PreferencesRepository
import kotlinx.coroutines.flow.Flow
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

    override suspend fun updateCards(offset: Int) {

        try {
            val data = dataSource.getDataAddress(
                num = 20,
                offset = offset
            ).data

            if (data != null) {
                dao.upsertCards(data.mapToCardEntity())
            }

        } catch (e: Exception) {
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