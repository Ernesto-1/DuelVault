package com.myapp.duelvault.home.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.duelvault.home.domain.favorite.FavoriteUseCase
import com.myapp.duelvault.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val useCase: FavoriteUseCase
) : ViewModel() {

    private val _limit = MutableStateFlow(20)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<FavoriteState> = _limit
        .flatMapLatest { limit ->
            useCase.getCards(limit = limit)
        }
        .map { result ->
            when (result) {
                is Resource.Loading -> {
                    uiState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    val prices = result.data.map { it.cardPrice }
                    val total = prices.sumOf { it.toDouble() }


                    FavoriteState(
                        isLoading = false,
                        cards = result.data.distinct(),
                        error = null,
                        total = total

                    )
                }

                is Resource.Failure -> {
                    uiState.value.copy(isLoading = false, error = "Error al cargar")
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoriteState(isLoading = true)
        )

    fun onEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.SaveFavorite -> handleChangeFavorite(event.id)
            is FavoriteEvent.DeleteFavorite -> handleDeleteFavorite(event.id)
            is FavoriteEvent.ChargingMoreCards -> loadMore()
        }
    }

    private fun loadMore() {
        if (!uiState.value.isLoading) {
            _limit.value += 20
        }
    }

    private fun handleChangeFavorite(id: Int) {
        viewModelScope.launch { useCase.saveFavorite(id = id) }
    }

    private fun handleDeleteFavorite(id: Int) {
        viewModelScope.launch { useCase.deleteFavorite(id = id) }
    }
}