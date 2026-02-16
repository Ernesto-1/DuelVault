package com.myapp.duelvault.utils.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.myapp.duelvault.BuildConfig
import com.myapp.duelvault.home.data.local.RoomDataBase
import com.myapp.duelvault.home.data.local.dao.DataCardDao
import com.myapp.duelvault.home.data.local.dao.FavoritesDao
import com.myapp.duelvault.home.data.remote.service.YGOApi
import com.myapp.duelvault.home.domain.HomeRepo
import com.myapp.duelvault.home.domain.HomeRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {


    @Binds
    abstract fun providesCardsRepository(repoHome: HomeRepoImpl): HomeRepo
    companion object {

        @Singleton
        @Provides
        fun providesOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().also {
                        it.setLevel(
                            HttpLoggingInterceptor.Level.HEADERS
                        )
                    }
                )
                .build()

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): RoomDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                RoomDataBase::class.java,
                "DuelValue_database"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries().build()
        }

        @Singleton
        @Provides
        fun provideRetrofitInstance(client: OkHttpClient): Retrofit {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.YGOPRODECK)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        @Singleton
        @Provides
        fun provideService(retrofit: Retrofit) = retrofit.create<YGOApi>()

        @Provides
        fun provideCardRoomDao(appDatabase: RoomDataBase): DataCardDao {
            return appDatabase.saveCards()
        }

        @Provides
        fun provideFavoritesRoomDao(appDatabase: RoomDataBase): FavoritesDao {
            return appDatabase.saveFavorites()
        }

    }
}