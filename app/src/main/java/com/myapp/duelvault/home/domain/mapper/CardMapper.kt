package com.myapp.duelvault.home.domain.mapper

import com.myapp.duelvault.home.data.local.Converters
import com.myapp.duelvault.home.data.local.entitys.CardEntity
import com.myapp.duelvault.home.data.remote.model.response.Data

fun List<Data?>.mapToCardEntity(): List<CardEntity> {
    val cards = mapNotNull { card ->
        card.mapCard()
    }
    return cards
}

fun Data?.mapCard() : CardEntity? {
    if (this == null ) {
        return null
    }
    this.cardImages
    return CardEntity(
        id = this.id ?: 0,
        name = this.name ?: "",
        type = this.type ?: "",
        frameType = this.frameType ?: "",
        desc = this.desc ?: "",
        atk = this.atk ?: -1,
        def = this.def ?: -1,
        level = this.level ?: -1,
        race = this.race ?: "",
        attribute = this.attribute ?: "",
        cardImages = Converters().fromCardImagesList(this.cardImages),
        cardPrices = Converters().fromCardPricesList(this.cardPrices)
    )
}
