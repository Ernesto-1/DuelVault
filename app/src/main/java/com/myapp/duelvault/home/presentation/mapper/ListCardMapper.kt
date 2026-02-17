package com.myapp.duelvault.home.presentation.mapper

import com.myapp.duelvault.home.data.local.Converters
import com.myapp.duelvault.home.data.local.entitys.CardWithFavorite
import com.myapp.duelvault.home.presentation.model.DataCard

fun List<CardWithFavorite>.mapToCardEntity(): List<DataCard> {
    return map { item ->
        item.mapListCard()
    }
}

fun CardWithFavorite.mapListCard(): DataCard {
    val entity = this.card
    val converters = Converters()

    val imageUrl = converters.toCardImagesList(entity.cardImages)
        ?.firstOrNull { it.imageUrl != null }?.imageUrl ?: ""

    val price = converters.toCardPricesList(entity.cardPrices)
        ?.firstOrNull { it.tcgplayerPrice != null }?.tcgplayerPrice ?: "Sin datos"

    return DataCard(
        id = entity.id,
        name = entity.name,
        type = entity.type,
        frameType = entity.frameType,
        cardImage = imageUrl,
        cardPrice = price,
        isFavorite = this.isFavorite
    )
}
