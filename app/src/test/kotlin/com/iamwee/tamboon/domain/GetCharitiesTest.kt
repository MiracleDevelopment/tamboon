package com.iamwee.tamboon.domain

import com.iamwee.tamboon.DataTest
import com.iamwee.tamboon.data.repository.CharityRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCharitiesTest {

    lateinit var useCase: GetCharitiesUseCase

    @Before
    fun setup() {
        val repository = mock<CharityRepository> {
            on { charities() }.doReturn(DataTest.charities)
        }

        useCase = GetCharitiesUseCase(repository)
    }


    @Test
    fun `Verify that can get charities`() {
        val result = runBlocking { useCase.execute(Unit) }
        assertTrue(result.isNotEmpty())
    }


}