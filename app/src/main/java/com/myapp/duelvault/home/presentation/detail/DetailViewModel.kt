package com.myapp.duelvault.home.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.duelvault.home.domain.detail.DetailUseCase
import com.myapp.duelvault.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: DetailUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val data: Int = checkNotNull(savedStateHandle["id"])

    private val _uiState = MutableStateFlow(DetailState())
    val uiState = _uiState.asStateFlow()

    init {
        onEvent(DetailEvent.GetCards(data))
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.GetCards -> {handleCards(event.id)}
            is DetailEvent.SaveFavorite -> {handleChangeFavorite(event.id)}
            is DetailEvent.DeleteFavorite -> {handleDeleteFavorite(event.id)}
        }
    }

    private fun handleCards(id: Int) {
        viewModelScope.launch {
            useCase.getCard(id).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        updateState { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        updateState { it.copy(isLoading = false, card = result.data) }
                    }
                    is Resource.Failure -> {
                        updateState {
                            it.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    private fun handleChangeFavorite(id: Int) {
        viewModelScope.launch { useCase.saveFavorite(id = id) }
    }

    private fun handleDeleteFavorite(id: Int) {
        viewModelScope.launch { useCase.deleteFavorite(id = id) }
    }

    private fun updateState(transform: (DetailState) -> DetailState) {
        _uiState.update(transform)
    }

}