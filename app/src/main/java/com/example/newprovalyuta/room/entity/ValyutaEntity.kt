package com.example.newprovalyuta.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newprovalyuta.R
import com.example.newprovalyuta.models.Icons

@Entity
data class ValyutaEntity(
    @PrimaryKey
    val code: String="",
    val date: String="",
    var cb_price: String="",
    val nbu_buy_price: String="",
    val nbu_cell_price: String="",
    var title: String="",
    var icon: Int = R.drawable.ic_uzbekistan_flag,
    var saved: Boolean=false
)
