package com.myapp.duelvault.home.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.myapp.duelvault.home.data.local.entitys.CardEntity
import com.myapp.duelvault.home.data.local.entitys.CardWithFavorite
import kotlinx.coroutines.flow.Flow

@Dao
interface DataCardDao {

    @Upsert
    suspend fun upsertCards(cards: List<CardEntity>)

    @Query(
        """
    SELECT 
        cards.*, 
        (favorites.id IS NOT NULL) AS isFavorite 
    FROM cards 
    LEFT JOIN favorites ON cards.id = favorites.id
    ORDER BY cards.id ASC 
    LIMIT :limit OFFSET 0
"""
    )
    fun getCards(limit: Int): Flow<List<CardWithFavorite>>

    @Query(
        """
    SELECT 
        cards.*, 
        1 AS isFavorite 
    FROM cards 
    INNER JOIN favorites ON cards.id = favorites.id
    ORDER BY cards.id ASC
    LIMIT :limit OFFSET 0
"""
    )
    fun getCardsFavorite(limit: Int): Flow<List<CardWithFavorite>>

    @Query("""
    SELECT 
        cards.*, 
        (favorites.id IS NOT NULL) AS isFavorite 
    FROM cards 
    LEFT JOIN favorites ON cards.id = favorites.id 
    WHERE cards.id = :id
""")
    fun getCard(id: Int): Flow<CardWithFavorite?>

    @Query("""DELETE FROM cards WHERE id NOT IN (SELECT id FROM favorites)""")
    fun deleteCards()

}
