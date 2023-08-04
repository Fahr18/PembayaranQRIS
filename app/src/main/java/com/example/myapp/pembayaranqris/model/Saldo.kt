package com.example.myapp.pembayaranqris.model

data class Saldo(
    val id: String,
    val bankSumber: String,
    val idTransaksi: String,
    val namaMerchant: String,
    val nominalTransaksi: Int
)