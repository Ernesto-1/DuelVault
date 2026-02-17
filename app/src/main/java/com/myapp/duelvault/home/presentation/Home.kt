package com.myapp.duelvault.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapp.duelvault.R
import com.myapp.duelvault.home.presentation.skeleton.HomeSkeleton
import com.myapp.duelvault.utils.components.CardItem
import com.myapp.duelvault.utils.components.PullRefresh
import com.myapp.duelvault.utils.components.TopBarGeneral
import com.myapp.duelvault.utils.navigation.AppDestination
import com.myapp.duelvault.utils.theme.backgroud
import com.myapp.duelvault.utils.toTime
import kotlinx.coroutines.launch

@Composable
fun Home(
    goNav: (AppDestination) -> Unit = {}, viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val userName = viewModel.userName.collectAsStateWithLifecycle().value
    val listState = rememberLazyGridState()
    val isRefreshing = remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val lastUpdate = viewModel.lastUpdate.collectAsStateWithLifecycle().value
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
    var clicked by remember { mutableStateOf(false) }
    if (clicked) {
        clicked = false
    }

    LaunchedEffect(isAtBottom) {
        if (isAtBottom) {
            viewModel.onEvent(HomeEvent.ChargingMoreCards)
        }
    }

    LaunchedEffect(isRefreshing.value) {
        if (isRefreshing.value) {
            viewModel.onEvent(HomeEvent.DeleteCards)
            isRefreshing.value = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), containerColor = backgroud, topBar = {
            TopBarGeneral(name = String.format(
                stringResource(R.string.greeting),
                userName
            ), contentRight = {
                OutlinedButton(onClick = {

                }, contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)) {
                    Text(stringResource(R.string.Button_favorites), fontSize = 10.sp, modifier = Modifier.clickable {
                        goNav(AppDestination.Favorite)
                    })
                }
            })
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (lastUpdate > 0) {
                Text(
                    "Last update: ${lastUpdate.toTime()}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            PullRefresh(isRefreshing = isRefreshing, modifier = Modifier, onRefresh = {
                isRefreshing.value = true
            }) {
                if (state.isLoading) {
                    HomeSkeleton()
                }
                LazyVerticalGrid(
                    state = listState,
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        state.cards,
                        key = { card -> card.id },
                        contentType = { "dv_card" }) { card ->
                        CardItem(card = card, clickIcon = {
                            if (card.isFavorite) {
                                viewModel.onEvent(HomeEvent.DeleteFavorite(card.id))
                            } else {
                                viewModel.onEvent(HomeEvent.SaveFavorite(card.id))
                            }
                        }, onItemClick = {
                            coroutine.launch {
                                if (!clicked) {
                                    goNav.invoke(AppDestination.Detail(card.id))
                                    clicked = true
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}
