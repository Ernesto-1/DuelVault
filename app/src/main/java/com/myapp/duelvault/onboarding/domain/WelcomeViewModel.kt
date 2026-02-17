package com.myapp.duelvault.onboarding.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.duelvault.utils.datastore.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val preferencesRepo: PreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WelcomeState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: WelcomeEvent) {
        when (event) {
            is WelcomeEvent.SaveName -> {
                handleSaveName(event.name)
            }
        }
    }

    private fun handleSaveName(name: String) {
        viewModelScope.launch {
            preferencesRepo.saveUserName(name)
            updateState { it.copy(isSuccess = true) }
        }
    }

    private fun updateState(transform: (WelcomeState) -> WelcomeState) {
        _uiState.update(transform)
    }

}