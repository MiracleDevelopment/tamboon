package com.iamwee.tamboon.data

import com.google.gson.annotations.SerializedName

data class Charity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo_url") val logoUrl: String
)