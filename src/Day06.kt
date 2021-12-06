import java.math.BigInteger
import java.util.*

fun main() {
    fun createSchool(input: List<String>) = School(
        input
            .flatMap { it.split(",") }
            .map { it.toLong() }
            .groupingBy { it }
            .fold(BigInteger.ZERO) { accumulator, _ -> accumulator.add(BigInteger.ONE) }
            .toMutableMap()
    )

    fun part1(input: List<String>) = createSchool(input).days(80).size()

    fun part2(input: List<String>) = createSchool(input).days(256).size()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == BigInteger.valueOf(5934L))
    check(part2(testInput) == BigInteger.valueOf(26984457539L))

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

private data class School(val fish: MutableMap<Long, BigInteger>) {
    private var queue: Queue<BigInteger> = LinkedList(listOf(BigInteger.ZERO, BigInteger.ZERO))

    fun size(): BigInteger {
        var value = fish.values.reduce { acc, bigInteger -> acc.add(bigInteger) }
        while (queue.isNotEmpty()) {
            value += queue.remove()
        }
        return value
    }

    fun days(days: Long): School {
        for (day in 0 until days) {
            val birthDay = day % 7
            val value = fish.getOrPut(birthDay) { BigInteger.ZERO }
            queue.add(value)
            fish[birthDay] = value + queue.remove()
        }
        return this
    }
}
