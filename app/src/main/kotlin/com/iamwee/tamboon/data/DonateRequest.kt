package com.iamwee.tamboon.data

data class DonateRequest(
    val name: String,
    val token: String,
    val amount: Long
)