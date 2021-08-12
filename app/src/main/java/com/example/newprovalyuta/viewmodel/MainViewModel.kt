package com.example.newprovalyuta.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newprovalyuta.repository.ValyutaRepository
import com.example.newprovalyuta.room.dao.ValyutaDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: ValyutaRepository) : ViewModel() {
    fun get() = repository.get()
    fun dao() = repository.valyutaDao
}