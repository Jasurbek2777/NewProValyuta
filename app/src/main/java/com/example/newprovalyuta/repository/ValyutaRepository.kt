package com.example.newprovalyuta.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newprovalyuta.R
import com.example.newprovalyuta.models.Icons
import com.example.newprovalyuta.models.Valyuta
import com.example.newprovalyuta.retrofit.ApiHelper
import com.example.newprovalyuta.room.dao.ValyutaDao
import com.example.newprovalyuta.room.entity.ValyutaEntity
import com.example.newprovalyuta.utils.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class ValyutaRepository @Inject constructor(var apiHelper: ApiHelper, var valyutaDao: ValyutaDao) {

    private var list = MutableLiveData<Resource<ArrayList<Valyuta>>>()
    fun get(): LiveData<Resource<ArrayList<Valyuta>>> {
        GlobalScope.launch {
            try {
                list.postValue(Resource.loading(null))
                apiHelper.getValyuta().let {
                    list.postValue(Resource.success(it as ArrayList<Valyuta>))
                }
            } catch (e: Exception) {
                list.postValue(Resource.error(e.message ?: "Error", null))
            }
        }
        return list
    }


}