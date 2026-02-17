package com.myapp.duelvault.home.presentation.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.myapp.duelvault.R
import com.myapp.duelvault.home.presentation.CardItem
import com.myapp.duelvault.home.presentation.ItemFavorite
import com.myapp.duelvault.home.presentation.TopBarGeneral
import com.myapp.duelvault.utils.theme.backgroud

@Composable
fun DetailCard(onBack: () -> Unit = {}, viewModel: DetailViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val density = LocalDensity.current

    Scaffold(containerColor = backgroud, topBar = {
        TopBarGeneral(name = "Detail", iconLeft = R.drawable.ic_dv_arrow_left, onClickIconLeft = {
            onBack.invoke()
        }, contentRight = {
            ItemFavorite(isFavorite = state.card.isFavorite, onItemClick = {
                if (state.card.id != -1) {
                    if (state.card.isFavorite) {
                        viewModel.onEvent(DetailEvent.DeleteFavorite(state.card.id))
                    } else {
                        viewModel.onEvent(DetailEvent.SaveFavorite(state.card.id))
                    }
                }
            })
        })
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(horizontal = 12.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(
                        state.card.cardImage
                    ).size(
                        with(density) { LocalConfiguration.current.screenWidthDp.dp.roundToPx() },
                        with(density) { 350.dp.roundToPx() }).crossfade(true).build(),
                    contentDescription = "Image_card",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(350.dp)
                )
                Text(
                    text = state.card.name,
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    minLines = 2
                )
                Text(
                    text = state.card.cardPrice + "$",
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_dv_info),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = state.card.frameType.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

            }
            if (state.card.atk != -1 || state.card.def != -1 || state.card.level != -1) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    colors = CardDefaults.cardColors(White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (state.card.atk != -1) {
                            Text(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("ATK ")
                                }
                                append(state.card.atk.toString())
                            })
                        }
                        if (state.card.def != -1) {
                            Text(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("DEF ")
                                }
                                append(state.card.def.toString())
                            })
                        }
                        if (state.card.level != -1) {
                            Text(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("LVL ")
                                }
                                append(state.card.level.toString())
                            })
                        }
                    }
                }

            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (state.card.attribute != "") {
                    Badge(
                        modifier = Modifier.padding(vertical = 8.dp),
                        containerColor = Color(0xFF8B4513)
                    ) {
                        Text(
                            text = state.card.attribute, color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }

                if (state.card.race != "") {
                    Badge(
                        modifier = Modifier.padding(vertical = 8.dp),
                        containerColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Text(
                            text = state.card.race,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }

            }
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Text(
                        text = state.card.desc,
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 20.sp
                    )
                }
            }

        }
    }

}