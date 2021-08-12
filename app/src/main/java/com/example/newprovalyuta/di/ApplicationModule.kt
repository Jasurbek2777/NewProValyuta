package com.example.newprovalyuta.di

import android.content.Context
import androidx.room.Room
import com.example.newprovalyuta.retrofit.ApiService
import com.example.newprovalyuta.room.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModulev {
    @Provides
    fun provideBaseUrl(): String = "https://nbu.uz/uz/exchange-rates/json/"


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService = provideRetrofit().create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, "my_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun probideDao(appDataBase: AppDataBase) = appDataBase.dao()
}