package com.myapp.duelvault.onboarding.domain.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.duelvault.utils.datastore.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferencesRepo: PreferencesRepository
) : ViewModel() {

    val userName: StateFlow<String> = preferencesRepo.userName
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

}