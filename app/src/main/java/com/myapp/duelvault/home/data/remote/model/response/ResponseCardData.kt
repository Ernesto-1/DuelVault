package com.myapp.duelvault.home.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class ResponseCardData(
    @SerializedName("data") var data: List<Data>? = null
)

data class Data(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("frameType") var frameType: String? = null,
    @SerializedName("desc") var desc: String? = null,
    @SerializedName("atk") var atk: Int? = null,
    @SerializedName("def") var def: Int? = null,
    @SerializedName("level") var level: Int? = null,
    @SerializedName("race") var race: String? = null,
    @SerializedName("attribute") var attribute: String? = null,
    @SerializedName("card_images") var cardImages: List<CardImages>? = null,
    @SerializedName("card_prices") var cardPrices: List<CardPrices>? = null,
)

data class CardPrices(

    @SerializedName("cardmarket_price") var cardmarketPrice: String? = null,
    @SerializedName("tcgplayer_price") var tcgplayerPrice: String? = null,
    @SerializedName("ebay_price") var ebayPrice: String? = null,
    @SerializedName("amazon_price") var amazonPrice: String? = null,
    @SerializedName("coolstuffinc_price") var coolstuffincPrice: String? = null

)

data class CardImages(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("image_url") var imageUrl: String? = null
)