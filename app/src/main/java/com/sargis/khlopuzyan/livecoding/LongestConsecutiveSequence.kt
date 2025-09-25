package com.sargis.khlopuzyan.livecoding

fun main() {
    val numbers =
        arrayOf(100, 4, 1, 2, 3, 0, 1, 2, 3, 4, 6, 7, 8, 9, 10, -7, -6, -5, -4, -3, -2, -1)
    val maxConsecutiveCount = longestConsecutive(numbers)
    println("maxConsecutiveCount: $maxConsecutiveCount")
}

fun longestConsecutive(numbers: Array<Int>, includeDuplicate: Boolean = false): Int {
    if (numbers.isEmpty()) return 0

    var maxCount = 1
    var count = 1

    for (i in 1 until numbers.size) {
        if (includeDuplicate) {
            if (numbers[i] - numbers[i - 1] == 1 || numbers[i] - numbers[i - 1] == 0) {
                count++
                if (count > maxCount) {
                    maxCount = count
                }
            } else {
                count = 1
            }
        } else {
            if (numbers[i] - numbers[i - 1] == 1) {
                count++
                if (count > maxCount) {
                    maxCount = count
                }
            } else if (numbers[i] - numbers[i - 1] != 0) {
                count = 1
            }
        }
    }
    return maxCount
}