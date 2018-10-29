package com.iamwee.tamboon.data.repository

import com.iamwee.tamboon.common.ClientErrorException
import com.iamwee.tamboon.common.NetworkConnectionException
import com.iamwee.tamboon.common.ServerException
import com.iamwee.tamboon.data.DonationRequest
import com.iamwee.tamboon.data.DonationErrorBody
import com.iamwee.tamboon.data.DonationResponse
import com.iamwee.tamboon.http.HttpManager
import com.iamwee.tamboon.http.TamboonService
import com.iamwee.tamboon.utils.NetworkHandler
import retrofit2.Call

class DefaultDonationRepository(
    private val handler: NetworkHandler,
    private val service: TamboonService
) : DonationRepository {

    override fun donate(request: DonationRequest): DonationResponse {
        if (handler.isConnected == true) {
            return request(service.donate(request), DonationResponse(false))
        } else {
            throw NetworkConnectionException()
        }
    }

    private fun <L> request(call: Call<L>, default: L): L {
        return try {
            val response = call.execute()
            if (response.isSuccessful) {
                response.body() ?: default
            } else {
                response.errorBody()?.let {
                    val error = try {
                        HttpManager.gson.fromJson<DonationErrorBody>(it.string(), DonationErrorBody::class.java)
                    } catch (e: Exception) {
                        throw Exception("Could not parse error message from server.")
                    }
                    throw ClientErrorException("${error.errorCode} : ${error.errorMessage}")
                }
                default
            }
        } catch (e: Exception) {
            throw ServerException()
        }
    }

}

interface DonationRepository {

    fun donate(request: DonationRequest): DonationResponse

}