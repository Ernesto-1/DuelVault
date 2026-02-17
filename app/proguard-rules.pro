# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep class com.myapp.duelvault.home.presentation.model.** { *; }
-keep class com.myapp.duelvault.home.data.remote.model.response.** { *; }
-keep class com.myapp.duelvault.home.data.remote.service.** { *; }

# Mantiener las clases de la librería de Material Design para evitar crashes.
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

-keep class com.myapp.duelvault.home.data.local.Converters { *; }

# Para Gson y TypeToken (estas son reglas estándar de Gson)
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken


-keep public class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

-dontwarn com.google.crypto.tink.subtle.Ed25519Sign$KeyPair
-dontwarn com.google.crypto.tink.subtle.Ed25519Verify
-dontwarn com.google.crypto.tink.subtle.X25519

# Mantiene todas las clases que están anotadas con @Serializable y sus miembros.
-keep @kotlinx.serialization.Serializable class * { *; }

# Mantiene las clases de serializadores generadas por el plugin.
-keep class **$$serializer { *; }

# Mantiene las clases internas de la librería de serialización.
-keep class kotlinx.serialization.internal.* { *; }

# Reemplaza 'com.mypets.duelvault.ome.data.remote.service' con el paquete de tus interfaces de API.
-keep interface com.myapp.duelvault.home.data.remote.service.YGOApi { *; }

# Reglas generales para OkHttp, Gson (si lo usas con Retrofit) y Retrofit.
-keep class retrofit2.** { *; }
-keep class com.google.gson.** { *; }
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

# Hilt generalmente añade sus propias reglas
# y puede solucionar problemas en algunas configuraciones.
-keep class dagger.hilt.internal.aggregatedroot.codegen.*
-keep class com.myapp.duelvault.Hilt_MainActivity.Hilt_** { *; }
-keep class hilt_aggregated_deps.** { *; }
-keep class dagger.hilt.android.internal.modules.*
-keep class dagger.hilt.android.internal.builders.*
-keep class dagger.hilt.android.internal.lifecycle.*
-keep class dagger.hilt.android.flags.*


# Mantiene los composables que podrían ser llamados a través de reflexión.
-keep public class * extends androidx.compose.runtime.Composer {
    public <init>();
}
-keep public class * implements androidx.compose.runtime.Composer {
    public <init>();
}
-keep class **.R$* {
    <fields>;
}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile