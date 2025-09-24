package com.sargis.khlopuzyan.livecoding

fun main() {
//    val text = "( (111+5+2*2* (3-(4-2)) * (105-5+111*2.2) * (2+85) +3* (4+1) +6+7*8))*(-4)"
//    val text = "-4 * ( (-1)*(111+5+2*2* (3-(4-2)) * (105-5+111*2.2) * (2+85) +3* (4+1) +6+7*8))*(-4)"
//    val text = "-4 * ( (-)*(111+5+2*2* (3-(4-2)) * (105-5+111*2.2) * (2+85) +3* (4+1) +6+7*8))*(-4)"
    val text = "-.4 * ((111+5+2*2* (3-(4-2)) * (105-5+111*2.2) * (2+85) +3* (4+1) +6+7*8))*(-4)"
//    val text = "( (-1)*(111+5+2*2* (3-(4-2)) * (105-5+111*2.2) * (2+85) +3* (4+1) +6+7*8))*(-4)"
    println("text: $text")
    println("RESULT: ${calculate(text)}")
}

fun calculate(text: String): Float {
    var finalText = text.replace(" ", "")

    if (finalText.isEmpty()) {
        throw IllegalArgumentException("Text cant be empty")
    }

    if (!containsOnlyDigitsAndOperations(finalText)) {
        throw IllegalArgumentException("Text contains not arithmetic operations and digit")
    }

    if (!isParenthesesAreInCorrectForm(finalText)) {
        throw IllegalArgumentException("Parentheses are not in correct format")
    }

    var foundedParenthesesCount = 0
    var parenthesesIndex = 0
    var resultText = ""

    finalText.forEachIndexed { index, c ->
        if (c == '(') {
            if (foundedParenthesesCount == 0) {
                resultText = if (resultText.isEmpty()) {
                    resultText + finalText.substring(parenthesesIndex, index)
                } else {
                    resultText + finalText.substring(parenthesesIndex + 1, index)
                }
                parenthesesIndex = index
            }
            foundedParenthesesCount++
        } else if (c == ')') {
            foundedParenthesesCount--

            if (foundedParenthesesCount < 0) {
                throw IllegalArgumentException("Parentheses are not in correct format")
            }

            if (foundedParenthesesCount == 0) {
                resultText =
                    resultText + calculate(finalText.substring(parenthesesIndex + 1, index))
                parenthesesIndex = index
            }
        }

        if (index == finalText.length - 1 && parenthesesIndex != finalText.length - 1 && parenthesesIndex != 0) {
            resultText = resultText + finalText.substring(parenthesesIndex + 1, index + 1)
        }
    }

    if (foundedParenthesesCount < 0) {
        throw IllegalArgumentException("Parentheses are not in correct format")
    }

    if (resultText.isEmpty()) {
        resultText = finalText
    }

    return calculationSimpleText(resultText)
}

/**
 * @param text should contain only digits and arithmetic operations (+, -, * and '/') and can't contain parentheses and spaces
 * */
private fun calculationSimpleText(text: String): Float {
    var digitStartIndex = -1
    var result1: Float? = null
    var operation1 = ' '
    var operation2 = ' '
    var result2: Float? = null

    text.forEachIndexed { index, c ->
        if (c.isDigit() || c == '.' || (index == 0 && c == '-') || (c == '-' && !text[index - 1].isDigit())) {
            if (digitStartIndex == -1) {
                digitStartIndex = index
            }

            if (index == text.length - 1) {

                val digit = if (digitStartIndex == -1) {
                    c.toString().safeCastToFloat()
                } else {
                    text.substring(digitStartIndex, text.length).safeCastToFloat()
                }

                if (result1 == null) {
                    return digit
                } else if (result2 == null) {
                    return doOperation(result1, digit, operation1)
                } else {
                    result2 = doOperation(result2, digit, operation2)
                    return doOperation(result1, result2, operation1)
                }
            }
        } else {
            val digit = text.substring(digitStartIndex, index).safeCastToFloat()

            if (result1 == null) {
                //1
                result1 = text.substring(0, index).safeCastToFloat()
                operation1 = c
            } else if (result2 == null) {
                if (operation1 == '*' || operation1 == '/') {
                    //1*
                    result1 = doOperation(result1, digit, operation1)
                    operation1 = c
                } else {
                    if (c == '+' || c == '-') {
                        //1+2+
                        result1 = doOperation(result1, digit, operation1)
                        operation1 = c
                    } else {
                        //1+2*
                        result2 = digit
                        operation2 = c
                    }
                }
            } else {
                if (c == '*' || c == '/') {
                    //1+2*3*
                    result2 = doOperation(result2, digit, operation2)
                    operation2 = c
                } else {
                    // 1+2*3+
                    result2 = doOperation(result2, digit, operation2)
                    result1 = doOperation(result1, result2, operation1)
                    operation1 = c
                    result2 = null
                    operation2 = ' '
                }
            }
            digitStartIndex = -1
        }
    }
    return result1 ?: 0f
}

private fun String.safeCastToFloat(): Float {
    return try {
//        if (this == "-") -1f else this.toFloat()
        this.toFloat()
    } catch (e: NumberFormatException) {
        if (this == "-") {
            throw IllegalArgumentException("Please provide correct text : '-' sign cant be used without number")
        }
        throw IllegalArgumentException("Please provide correct text")
    }
}

private fun doOperation(a: Float, b: Float, operationType: Char): Float {
    return when (operationType) {
        '+' -> (a + b).toFloat()
        '-' -> (a - b).toFloat()
        '*' -> (a * b).toFloat()
        '/' -> (a / b).toFloat()
        else -> throw IllegalArgumentException("Wrong Operation: $operationType")
    }
}

private fun containsOnlyDigitsAndOperations(inputString: String): Boolean {
    // Regex to match digits (0-9) and common arithmetic operators (+, -, *, /, ., parentheses)
    // The ^ and $ ensure the entire string matches the pattern
    val regex = Regex("^[0-9+\\-*/().\\s]*$")
//    val regex = Regex("^[0-9+\\-*/()\\s]*$") // without '.'
    return inputString.matches(regex)
}

private fun isParenthesesAreInCorrectForm(text: String): Boolean {
    var leftAndRightParenthesesDiff = 0
    for (c in text) {
        if (c == '(') {
            leftAndRightParenthesesDiff++
        } else if (c == ')') {
            leftAndRightParenthesesDiff--
            if (leftAndRightParenthesesDiff < 0) {
                return false
            }
        }
    }
    return leftAndRightParenthesesDiff == 0
}