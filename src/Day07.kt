import kotlin.math.pow

fun main() {
    fun getCrabs(input: List<String>) = input
        .flatMap {
            it.split(",")
                .map { it.toInt() }
        }

    fun part1(input: List<String>): Long {
        val crabs = getCrabs(input)
        val min = crabs.minOf { it }.toLong()
        val max = crabs.maxOf { it }.toLong()

        return (min..max).minOf { position ->
            crabs.sumOf {
                when (it < position) {
                    true -> position - it
                    false -> it - position
                }
            }
        }
    }

    fun part2(input: List<String>): Long {
        val crabs = getCrabs(input)
        val min = crabs.minOf { it }.toLong()
        val max = crabs.maxOf { it }.toLong()

        return (min..max).minOf { position ->
            crabs.sumOf {
                when (it < position) {
                    true -> (position - it).let { distance -> (.5 * distance.toDouble().pow(2) + .5 * distance).toLong() }
                    false -> (it - position).let { distance -> (.5 * distance.toDouble().pow(2) + .5 * distance).toLong() }
                }
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37L)
    check(part2(testInput) == 168L)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
