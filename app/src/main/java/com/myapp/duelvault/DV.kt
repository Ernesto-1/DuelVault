package com.myapp.duelvault

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DV: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}