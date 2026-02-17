package com.myapp.duelvault.utils.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.myapp.duelvault.home.presentation.model.DataCard

@Composable
fun CardItem(
    card: DataCard,
    isDetail: Boolean = false,
    imageSize: Int = 150,
    onItemClick: () -> Unit = {},
    clickIcon: () -> Unit = {}
) {
    val density = LocalDensity.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick.invoke()
            }, shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(
                    card.cardImage
                ).size(
                    with(density) { LocalConfiguration.current.screenWidthDp.dp.roundToPx() },
                    with(density) { imageSize.dp.roundToPx() }).crossfade(true).build(),
                contentDescription = "Image_card",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(imageSize.dp)
            )
            Text(
                card.name,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                maxLines = 2,
                minLines = 2
            )
            if (isDetail) {
                Text(card.type, fontSize = 10.sp)
                Text(card.cardPrice + "$", fontSize = 10.sp)
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(card.cardPrice + "$", fontSize = 10.sp)
                    ItemFavorite(isFavorite = card.isFavorite) {
                        clickIcon.invoke()
                    }
                }
            }
        }
    }
}