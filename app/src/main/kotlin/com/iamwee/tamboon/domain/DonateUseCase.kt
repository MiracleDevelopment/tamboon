package com.iamwee.tamboon.domain

import com.iamwee.tamboon.data.DonateRequest
import com.iamwee.tamboon.data.DonationResponse
import com.iamwee.tamboon.data.repository.DonationRepository

class DonateUseCase(
    private val repository: DonationRepository
): UseCase<DonateRequest, DonationResponse>() {

    override suspend fun execute(params: DonateRequest) = repository.donate(params)

}