import com.android.sdklib.AndroidVersion.VersionCodes.VANILLA_ICE_CREAM
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
}

val localProperties = Properties()
val localPropertiesFile =
    rootProject.file("local.properties")

if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
    try {
        localPropertiesFile.inputStream().use { fis ->
            localProperties.load(fis)
        }
    } catch (e: Exception) {
        println("ADVERTENCIA: No se pudo cargar el archivo local.properties. ${e.message}")
    }
} else {
    println("El archivo local.properties no fue encontrado en la raíz del proyecto.")
}


fun Properties.getPropertyOrDefault(propertyName: String, defaultValue: String): String {
    val value = this.getProperty(propertyName)
    return if (value == null || value.trim().isEmpty()) {
        println("ADVERTENCIA: '${propertyName}' no está en local.properties o está vacía. Usando valor por defecto: '${defaultValue}'")
        defaultValue
    } else {
        value
    }
}



android {
    namespace = "com.myapp.duelvault"
    compileSdk = 36
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        applicationId = "com.myapp.duelvault"
        minSdk = 28
        targetSdk = VANILLA_ICE_CREAM
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    flavorDimensions += "environment"

    productFlavors {

        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"

            buildConfigField(
                "String",
                "YGOPRODECK",
                "\"${
                    localProperties.getPropertyOrDefault(
                        "YGOPRODECK",
                        "DEFAULT_DEV_YGOPRODECK_KEY_FALLBACK"
                    )
                }\""
            )
        }
        create("prod") {
            dimension = "environment"
            buildConfigField(
                "String",
                "YGOPRODECK",
                "\"${
                    localProperties.getPropertyOrDefault(
                        "YGOPRODECK",
                        "DEFAULT_DEV_YGOPRODECK_KEY_FALLBACK"
                    )
                }\""
            )
        }

        signingConfigs {
            create("release") {
                keyAlias = "my_key_alias"
                keyPassword = "my_key_password"
                storeFile = file("my_keystore_example.jks")
                storePassword = "my_keystore_example_password"
            }
        }


        buildTypes {
            getByName("debug") {
                isMinifyEnabled = false
            }
            getByName("release") {
                isMinifyEnabled = true
                signingConfig = signingConfigs.getByName("release")
                ndk {
                    debugSymbolLevel = "FULL"
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging.interceptor)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // ViewModel
    implementation(libs.androidx.viewmodel)

    // LiveData
    implementation(libs.androidx.livedata)
    implementation(libs.androidx.runtime.livedata)

    // WorkManager
    implementation(libs.androidx.work.runtime)

    // Hilt
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    //DataStore
    implementation(libs.dataStore)
    implementation(libs.androidx.datastore.core.android)

    // Navigation
    implementation(libs.navigation.compose)
}