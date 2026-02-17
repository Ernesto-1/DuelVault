package com.myapp.duelvault.onboarding.domain

sealed class WelcomeEvent {

    data class SaveName(val name: String) :  WelcomeEvent()
}