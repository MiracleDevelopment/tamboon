package com.iamwee.tamboon.data

import com.google.gson.annotations.SerializedName

data class DonationErrorBody(
    @SerializedName("success") val success: String,
    @SerializedName("error_code") val errorCode: String,
    @SerializedName("error_message") val errorMessage: String
)