package com.myapp.duelvault.home.presentation.mapper

import com.myapp.duelvault.home.data.local.Converters
import com.myapp.duelvault.home.data.local.entitys.CardWithFavorite
import com.myapp.duelvault.home.presentation.model.DataDetail

fun CardWithFavorite.mapDetailCard(): DataDetail {
    val entity = this.card
    val converters = Converters()

    val imageUrl = converters.toCardImagesList(entity.cardImages)
        ?.firstOrNull { it.imageUrl != null }?.imageUrl ?: ""

    val price = converters.toCardPricesList(entity.cardPrices)
        ?.firstOrNull { it.tcgplayerPrice != null }?.tcgplayerPrice ?: "Sin datos"

    return DataDetail(
        id = entity.id,
        name = entity.name,
        type = entity.type,
        frameType = entity.frameType,
        cardImage = imageUrl,
        cardPrice = price,
        isFavorite = this.isFavorite,
        desc = entity.desc,
        atk = entity.atk,
        def = entity.def,
        level = entity.level,
        race = entity.race,
        attribute = entity.attribute,
        cardImages = entity.cardImages,
        cardPrices = entity.cardPrices
    )
}


