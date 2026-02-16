package com.myapp.duelvault.home.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myapp.duelvault.R
import com.myapp.duelvault.home.presentation.ItemFavorite
import com.myapp.duelvault.home.presentation.TopBarGeneral

@Composable
fun DetailCard(onBack: () -> Unit = {}, viewModel: DetailViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(topBar = {
        TopBarGeneral(name = "Detail", iconLeft = R.drawable.ic_dv_arrow_left, onClickIconLeft = {
            onBack.invoke()
        }, contentRight = {
            ItemFavorite(isFavorite = state.card.isFavorite, onItemClick = {
                if (state.card.id != -1){
                    if (state.card.isFavorite) {
                        viewModel.onEvent(DetailEvent.DeleteFavorite(state.card.id))
                    } else {
                        viewModel.onEvent(DetailEvent.SaveFavorite(state.card.id))
                    }
                }
            })
        })
    }) {
        Column(modifier = Modifier.padding(it)) {

        }
    }

}