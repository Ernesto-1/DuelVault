package com.myapp.duelvault.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.duelvault.home.domain.HomeUseCase
import com.myapp.duelvault.utils.Resource
import com.myapp.duelvault.utils.datastore.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase,
    private val preferencesRepo: PreferencesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _limit = savedStateHandle.getMutableStateFlow("limit_key", 20)

    val lastUpdate: StateFlow<Long> = preferencesRepo.lastUpdate
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val userName: StateFlow<String> = preferencesRepo.userName
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeState> = _limit
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
                    HomeState(
                        isLoading = false,
                        cards = result.data.distinct(),
                        error = null
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
            initialValue = HomeState(isLoading = true)
        )

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SaveFavorite -> {
                handleChangeFavorite(event.id)
            }

            is HomeEvent.DeleteFavorite -> {
                handleDeleteFavorite(event.id)
            }

            is HomeEvent.ChargingMoreCards -> {
                loadMore()
            }
            is HomeEvent.DeleteCards -> {
                handleDelete()
            }
        }
    }

    private fun loadMore() {
        if (!uiState.value.isLoading) {
            _limit.value += 20
        }
    }

    private fun handleDelete(){
        viewModelScope.launch {
            useCase.deleteCards()
            _limit.value = 20
            preferencesRepo.saveLastUpdate(System.currentTimeMillis())
        }
    }

    private fun handleChangeFavorite(id: Int) {
        viewModelScope.launch { useCase.saveFavorite(id = id) }
    }

    private fun handleDeleteFavorite(id: Int) {
        viewModelScope.launch { useCase.deleteFavorite(id = id) }
    }
}