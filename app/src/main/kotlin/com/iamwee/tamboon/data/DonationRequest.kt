package com.iamwee.tamboon.data

data class DonationRequest(
    val name: String,
    val token: String,
    val amount: Long
)