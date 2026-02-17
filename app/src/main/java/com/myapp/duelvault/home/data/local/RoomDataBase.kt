package com.myapp.duelvault.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myapp.duelvault.home.data.local.dao.DataCardDao
import com.myapp.duelvault.home.data.local.dao.FavoritesDao
import com.myapp.duelvault.home.data.local.entitys.CardEntity
import com.myapp.duelvault.home.data.local.entitys.CardsFavoritesEntity
import com.myapp.duelvault.home.data.remote.model.response.CardImages
import com.myapp.duelvault.home.data.remote.model.response.CardPrices

@Database(
    entities = [CardEntity::class, CardsFavoritesEntity::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun saveCards(): DataCardDao
    abstract fun saveFavorites(): FavoritesDao
}

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromCardImagesList(value: List<CardImages>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCardImagesList(value: String?): List<CardImages>? {
        if (value == null) return null
        val type = object : TypeToken<List<CardImages>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCardPricesList(value: List<CardPrices>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCardPricesList(value: String?): List<CardPrices>? {
        if (value == null) return null
        val type = object : TypeToken<List<CardPrices>>() {}.type
        return gson.fromJson(value, type)
    }
}