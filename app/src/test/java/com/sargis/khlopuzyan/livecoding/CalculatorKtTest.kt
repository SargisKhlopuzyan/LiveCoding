package com.sargis.khlopuzyan.livecoding

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorKtTest {

    @Test
    fun `correct string arithmetic operations_returns valid result v1`() {
        val text = "-.4 * 8"
        assertEquals(-3.2f, calculate(text))
    }

    @Test
    fun `empty arithmetic operations_throws IllegalArgumentException exception`() {
        val text = ""
        Assert.assertThrows(
            "Text cant be empty",
            IllegalArgumentException::class.java
        ) {
            calculate(text)
        }
    }

    @Test
    fun `empty arithmetic operations_throws IllegalArgumentException exception v2`() {
        val text = "()"
        Assert.assertThrows(
            "Text cant be empty",
            IllegalArgumentException::class.java
        ) {
            calculate(text)
        }
    }

    @Test
    fun `correct string arithmetic operations_returns valid result`() {
        val text = "-.4 * ((111+5+2*2* (3-(4-2)) * (105-5+111*2.2) * (2+85) +3* (4+1) +6+7*8))*(-4)"
        assertEquals(191959.36f, calculate(text))
    }

    @Test
    fun `arithmetic operations with incorrect parentheses format_throws IllegalArgumentException exception`() {
        val text =
            "(-.4 * ((111+5+2*2* (3-(4-2)) * (105-5+111*2.2) * (2+85) +3* (4+1) +6+7*8))*(-4)"
        Assert.assertThrows(
            "Parentheses are not in correct format",
            IllegalArgumentException::class.java
        ) {
            calculate(text)
        }
    }

    @Test
    fun `arithmetic operations with incorrect number format_throws IllegalArgumentException exception`() {
        val text =
            "(-0...4 * ((111+5+2*2* (3-(4-2)) * (105-5+111*2.2) * (2+85) +3* (4+1) +6+7*8))*(-4)"
        Assert.assertThrows(
            "Please provide correct text",
            IllegalArgumentException::class.java
        ) {
            calculate(text)
        }
    }

    @Test
    fun `arithmetic operations with incorrect symbol_throws exception`() {
        val text =
            "(-&4 * ((111+5+2*2* (3-(4-2)) * (105-5+111*2.2) * (2+85) +3* (4+1) +6+7*8))*(-4)"
        Assert.assertThrows(
            "Text contains not arithmetic operations and digit",
            IllegalArgumentException::class.java
        ) {
            calculate(text)
        }
    }
}
