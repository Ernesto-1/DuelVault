package com.myapp.duelvault.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.myapp.duelvault.R
import com.myapp.duelvault.home.presentation.mapper.DataCard
import com.myapp.duelvault.utils.navigation.AppDestination
import com.myapp.duelvault.utils.theme.backgroud
import com.myapp.duelvault.utils.theme.favoriteColor

@Composable
fun Home(
    goNav: (AppDestination) -> Unit = {}, viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val listState = rememberLazyGridState()
    val isRefreshing = remember { mutableStateOf(false) }

    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.lastOrNull()
                lastVisibleItem?.index == layoutInfo.totalItemsCount - 1
            }
        }
    }

    LaunchedEffect(isAtBottom) {
        if (isAtBottom) {
            viewModel.onEvent(HomeEvent.ChargingMoreCards)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), containerColor = backgroud, topBar = {
            TopBarGeneral(name = state.cards.size.toString(), contentRight = {
                OutlinedButton(onClick = {

                }, contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)) {
                    Text("Mis favoritos", fontSize = 10.sp, modifier = Modifier.clickable {
                        goNav(AppDestination.Favorite)
                    })
                }
            })
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PullRefresh(isRefreshing = isRefreshing, modifier = Modifier) {

                LazyVerticalGrid(
                    state = listState,
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.cards, key = { it.id },contentType = { "dv_card" }) { card ->
                        CardItem(card = card, isFavorite = card.isFavorite, clickIcon = {
                            if (card.isFavorite) {
                                viewModel.onEvent(HomeEvent.DeleteFavorite(card.id))
                            } else {
                                viewModel.onEvent(HomeEvent.SaveFavorite(card.id))
                            }
                        }, onItemClick = {
                            goNav.invoke(AppDestination.Detail(card.id))
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun CardItem(
    card: DataCard,
    onItemClick: () -> Unit = {},
    isFavorite: Boolean = false,
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
                    with(density) { 150.dp.roundToPx() }).crossfade(true).build(),
                contentDescription = "Image_card",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(150.dp)
            )
            Text(
                card.name,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                maxLines = 2,
                minLines = 2
            )
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(card.cardPrice + "$", fontSize = 10.sp)
                ItemFavorite(isFavorite = isFavorite) {
                    clickIcon.invoke()
                }
            }
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarGeneral(
    name: String,
    iconLeft: Int? = null,
    onClickIconLeft: () -> Unit = {},
    contentRight: @Composable () -> Unit = {}
) {
    Surface(shadowElevation = 4.dp) {
        TopAppBar(
            title = {
                Text(
                    text = name,
                    modifier = Modifier.padding(end = 10.dp),
                    textAlign = TextAlign.End,
                    fontSize = 16.sp
                )
            }, navigationIcon = {
                if (iconLeft != null) {
                    IconButton(onClick = { onClickIconLeft.invoke() }) {
                        Icon(
                            painter = painterResource(iconLeft),
                            contentDescription = "Icon_left",
                        )
                    }
                }

            }, actions = {
                Row(modifier = Modifier.padding(end = 8.dp)) {
                    contentRight()
                }
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
        )
    }
}

@Composable
fun PullRefresh(
    modifier: Modifier, isRefreshing: MutableState<Boolean>, content: @Composable () -> Unit = {}
) {
    val onRefresh: () -> Unit = {
        isRefreshing.value = true
    }
    PullToRefreshBox(
        isRefreshing = isRefreshing.value,
        onRefresh = onRefresh,
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        content()
    }
}