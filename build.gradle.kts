// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Android
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    // Kotlin
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false

    // DI / Otros
    alias(libs.plugins.hilt.android) apply false
}