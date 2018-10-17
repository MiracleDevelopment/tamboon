package com.iamwee.tamboon.data.repository

import com.iamwee.tamboon.common.NetworkConnectionException
import com.iamwee.tamboon.common.ServerException
import com.iamwee.tamboon.data.Charity
import com.iamwee.tamboon.http.TamboonService
import com.iamwee.tamboon.utils.NetworkHandler
import retrofit2.Call

interface CharityRepository {

    fun charities(): List<Charity>

}

class DefaultCharityRepository(
    private val networkHandler: NetworkHandler,
    private val service: TamboonService
) : CharityRepository {

    override fun charities(): List<Charity> {
        return when (networkHandler.isConnected) {
            true -> request(service.getCharities(), emptyList())
            else -> throw NetworkConnectionException()
        }
    }

    private fun <L> request(call: Call<L>, default: L): L {
        return try {
            val response = call.execute()
            if (response.isSuccessful) {
                response.body() ?: default
            } else {
                //No anything to handle right now so i'm gonna throw some exception
                default
            }
        } catch (e: Exception) {
            throw ServerException()
        }
    }

}