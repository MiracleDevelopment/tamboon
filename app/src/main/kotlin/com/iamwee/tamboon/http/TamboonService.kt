package com.iamwee.tamboon.http

import com.iamwee.tamboon.data.Charity
import com.iamwee.tamboon.data.DonationRequest
import com.iamwee.tamboon.data.DonationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TamboonService {

    @GET("charities")
    fun getCharities(): Call<List<Charity>>

    @POST("donations")
    fun donate(@Body body: DonationRequest): Call<DonationResponse>

}