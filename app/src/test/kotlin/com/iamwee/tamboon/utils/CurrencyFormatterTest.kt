package com.iamwee.tamboon.utils

import org.junit.Assert.*
import org.junit.Test

class CurrencyFormatterTest {


    @Test
    fun testParseSomeCurrency() {
        assertEquals("1,000", CurrencyFormatter.format(1_000))
    }

    @Test
    fun testParseOneMillion() {
        assertEquals("1,000,000", CurrencyFormatter.format(1_000_000))
    }

}