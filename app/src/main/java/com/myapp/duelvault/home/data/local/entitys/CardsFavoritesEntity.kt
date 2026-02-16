package com.myapp.duelvault.home.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class CardsFavoritesEntity(
    @PrimaryKey val id: Int,
)
