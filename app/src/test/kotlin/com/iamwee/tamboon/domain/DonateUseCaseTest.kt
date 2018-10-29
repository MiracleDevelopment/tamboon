package com.iamwee.tamboon.domain

import com.iamwee.tamboon.DataTest
import com.iamwee.tamboon.FakeDanationRepository
import com.iamwee.tamboon.data.DonationRequest
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DonateUseCaseTest {

    private lateinit var useCase: DonateUseCase

    @Before
    fun setup() {
        useCase = DonateUseCase(FakeDanationRepository())
    }

    @Test
    fun `Input donate request then should shown information as the same`() {
        val name = "Awesome name"
        val token = "Awesome token"
        val amount = 50_000L
        val request = DonationRequest(
            name = name,
            token = token,
            amount = amount
        )

        assertEquals(name, request.name)
        assertEquals(token, request.token)
        assertEquals(amount, request.amount)
    }

    @Test
    fun `Donate with incorrect token then donation shouldn't successfully`() {
        val request = DonationRequest(
            name = "awesome name",
            token = "some incorrect token that taken from omise public token",
            amount = 4000L
        )
        val result = runBlocking { useCase.execute(request) }

        assertEquals(false, result.success)
    }

    @Test
    fun `Donate with correct information then donation should successfully`() {
        val result = runBlocking { useCase.execute(DataTest.correctCard) }

        assertEquals(true, result.success)
    }

}