package com.iamwee.tamboon

import com.iamwee.tamboon.data.DonateRequest
import com.iamwee.tamboon.data.DonationResponse
import com.iamwee.tamboon.data.repository.DonationRepository

class FakeDanationRepository : DonationRepository {

    override fun donate(request: DonateRequest): DonationResponse {
        return DonationResponse(request == DataTest.correctCard)
    }
}