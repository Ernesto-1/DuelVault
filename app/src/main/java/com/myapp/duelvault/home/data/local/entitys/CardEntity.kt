package com.myapp.duelvault.home.data.local.entitys

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey val id: Int,
    val name: String = "",
    val type: String = "",
    val frameType: String = "",
    val desc: String = "",
    val atk: Int = -1,
    val def: Int = -1,
    val level: Int = -1,
    val race: String = "",
    val attribute: String = "",
    val cardImages: String = "",
    val cardPrices: String = ""
)

data class CardWithFavorite(
    @Embedded val card: CardEntity,
    val isFavorite: Boolean
)