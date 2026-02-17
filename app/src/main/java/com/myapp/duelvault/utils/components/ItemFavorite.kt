package com.myapp.duelvault.utils.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.myapp.duelvault.R
import com.myapp.duelvault.utils.theme.favoriteColor

@Composable
fun ItemFavorite(
    color: Color = favoriteColor, isFavorite: Boolean = false, onItemClick: () -> Unit = {}
) {
    Icon(
        painter = painterResource(R.drawable.ic_dv_favorite),
        "",
        modifier = Modifier.clickable { onItemClick.invoke() },
        tint = if (isFavorite) color else Color.Black,
    )
}