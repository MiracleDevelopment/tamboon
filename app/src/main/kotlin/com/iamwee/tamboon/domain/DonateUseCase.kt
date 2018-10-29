package com.iamwee.tamboon.domain

import com.iamwee.tamboon.data.DonationRequest
import com.iamwee.tamboon.data.DonationResponse
import com.iamwee.tamboon.data.repository.DonationRepository

class DonateUseCase(
    private val repository: DonationRepository
): UseCase<DonationRequest, DonationResponse>() {

    override suspend fun execute(params: DonationRequest) = repository.donate(params)

}