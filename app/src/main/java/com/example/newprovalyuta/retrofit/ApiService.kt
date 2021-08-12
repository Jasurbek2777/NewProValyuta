package com.example.newprovalyuta.retrofit

import com.example.newprovalyuta.models.Valyuta
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    suspend fun getData(): List<Valyuta>
}