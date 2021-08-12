package com.example.newprovalyuta.retrofit

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ApiHelper @Inject constructor(var apiService: ApiService){
    suspend fun getValyuta() = apiService.getData()
}