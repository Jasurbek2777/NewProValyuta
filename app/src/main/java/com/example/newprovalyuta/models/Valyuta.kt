package com.example.newprovalyuta.models

data class Valyuta(
    var ic_code: String,
    val cb_price: String,
    val code: String,
    val date: String,
    val nbu_buy_price: String,
    val nbu_cell_price: String,
    val title: String
)