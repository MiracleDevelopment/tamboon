package com.iamwee.tamboon

import com.iamwee.tamboon.data.DonationRequest
import com.iamwee.tamboon.data.DonationResponse
import com.iamwee.tamboon.data.repository.DonationRepository

class FakeDanationRepository : DonationRepository {

    override fun donate(request: DonationRequest): DonationResponse {
        return DonationResponse(request == DataTest.correctCard)
    }
}