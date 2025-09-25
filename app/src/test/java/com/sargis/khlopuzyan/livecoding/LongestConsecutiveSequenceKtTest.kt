package com.sargis.khlopuzyan.livecoding

import org.junit.Assert
import org.junit.Test

class LongestConsecutiveSequenceKtTest {

    @Test
    fun `empty array_returns 0`() {
        val numbers = arrayOf<Int>()
        Assert.assertEquals(0, longestConsecutive(numbers))
    }

    @Test
    fun `array with 1 element_returns 1`() {
        val numbers = arrayOf<Int>(1989)
        Assert.assertEquals(1, longestConsecutive(numbers))
    }

    @Test
    fun `array containing n consecutive _returns n`() {
        val numbers =
            arrayOf(100, 4, 1, 2, 3, 0, 1, 2, 2, 2, 2, 2, 2, 2, 3, 4, 6, 7, 8, 9, 10, -7, -6, -5, -4, -3, -2, -1, -1)
        Assert.assertEquals(7, longestConsecutive(numbers))
    }
}