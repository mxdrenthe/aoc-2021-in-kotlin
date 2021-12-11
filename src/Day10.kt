import java.util.*

fun main() {
    val openChar = listOf('(', '[', '{', '<')

    fun checkClosingChar(char: Char, compare: Char) = when (char) {
        ')' -> compare == '('
        ']' -> compare == '['
        '}' -> compare == '{'
        '>' -> compare == '<'
        else -> throw Exception("Invalid Char found")
    }

    fun scoreEndChar(char: Char) = when (char) {
        ')' -> 3L
        ']' -> 57L
        '}' -> 1197L
        '>' -> 25137L
        else -> 0
    }

    fun scoreClosingChar(char: Char) = when (char) {
        '(' -> 1L
        '[' -> 2L
        '{' -> 3L
        '<' -> 4L
        else -> 0
    }

    fun checkLine(line: String): Pair<Status, Long> {
        val stack = ArrayDeque<Char>()

        line.forEach { char ->
            if (openChar.contains(char)) {
                stack.push(char)
            } else {
                val previousOpen = stack.pop()
                if (!checkClosingChar(char, previousOpen)) {
                    return Pair(Status.CORRUPTED, scoreEndChar(char))
                }
            }
        }

        var sum = 0L
        while (stack.isNotEmpty()) {
            val closingChar = stack.pop()
            sum *= 5
            sum += scoreClosingChar(closingChar)
        }
        return Pair(Status.INCOMPLETE, sum)
    }

    fun part1(input: List<String>) = input
        .map { checkLine(it) }
        .filter { it.first == Status.CORRUPTED }
        .sumOf { it.second }


    fun part2(input: List<String>): Long {
        val incomplete = input
            .map { checkLine(it) }
            .filter { it.first == Status.INCOMPLETE }
            .sortedBy { it.second }

        return incomplete[incomplete.size / 2].second
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397L)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

private enum class Status {
    CORRUPTED, INCOMPLETE
}
