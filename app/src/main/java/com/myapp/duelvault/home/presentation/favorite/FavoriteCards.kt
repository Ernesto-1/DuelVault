package com.myapp.duelvault.home.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapp.duelvault.R
import com.myapp.duelvault.home.presentation.CardItem
import com.myapp.duelvault.home.presentation.HomeEvent
import com.myapp.duelvault.home.presentation.LazyCards
import com.myapp.duelvault.home.presentation.TopBarGeneral
import com.myapp.duelvault.utils.navigation.AppDestination
import com.myapp.duelvault.utils.theme.backgroud

@Composable
fun FavoriteCards(
    goNav: (AppDestination) -> Unit = {},
    onBack: () -> Unit = {},
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val listState = rememberLazyGridState()

    Scaffold(containerColor = backgroud, topBar = {
        TopBarGeneral(
            name = "Mis favoritos",
            iconLeft = R.drawable.ic_dv_arrow_left,
            onClickIconLeft = {
                onBack.invoke()
            })
    }) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            columns = GridCells.Fixed(2),
            state = listState,
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.cards) { card ->
                CardItem(card = card, isFavorite = card.isFavorite, clickIcon = {
                    if (card.isFavorite) {
                        viewModel.onEvent(FavoriteEvent.DeleteFavorite(card.id))
                    } else {
                        viewModel.onEvent(FavoriteEvent.SaveFavorite(card.id))
                    }
                }, onItemClick = {
                    goNav.invoke(AppDestination.Detail(card.id))
                })
            }
        }
    }
}