package com.myapp.duelvault.home.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.myapp.duelvault.home.data.local.entitys.CardsFavoritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    fun getIds(): Flow<List<CardsFavoritesEntity>>

    @Query("DELETE FROM favorites Where id = :id")
    suspend fun deleteCards(id: Int)

    @Query("INSERT INTO favorites (id) VALUES (:id)")
    suspend fun saveFavorite(id: Int)




}