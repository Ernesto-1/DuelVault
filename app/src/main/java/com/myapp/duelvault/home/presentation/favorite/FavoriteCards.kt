package com.myapp.duelvault.home.presentation.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapp.duelvault.R
import com.myapp.duelvault.home.presentation.skeleton.HomeSkeleton
import com.myapp.duelvault.utils.components.CardItem
import com.myapp.duelvault.utils.components.TopBarGeneral
import com.myapp.duelvault.utils.navigation.AppDestination
import com.myapp.duelvault.utils.theme.backgroud
import kotlinx.coroutines.launch

@Composable
fun FavoriteCards(
    goNav: (AppDestination) -> Unit = {},
    onBack: () -> Unit = {},
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val listState = rememberLazyGridState()
    val coroutine = rememberCoroutineScope()
    var clicked by remember { mutableStateOf(false) }
    if (clicked) {
        clicked = false
    }

    Scaffold(containerColor = backgroud, topBar = {
        TopBarGeneral(
            name = stringResource(R.string.Button_favorites),
            iconLeft = R.drawable.ic_dv_arrow_left,
            onClickIconLeft = {
                onBack.invoke()
            },
            contentRight = {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.Collection_value))
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                append("$${String.format("%.2f", state.total)}")
                            }
                        },
                        fontSize = 10.sp
                    )
                }
            })
    }) {
        if (state.isLoading) {
            HomeSkeleton()
        }
        if (state.cards.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_empty_state),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxWidth().size(200.dp),
                    contentScale = ContentScale.Fit
                )
                Text(stringResource(R.string.empty_favorite))
            }
        } else {
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
                    CardItem(card = card, clickIcon = {
                        if (card.isFavorite) {
                            viewModel.onEvent(FavoriteEvent.DeleteFavorite(card.id))
                        } else {
                            viewModel.onEvent(FavoriteEvent.SaveFavorite(card.id))
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