import kotlin.math.ceil

fun main() {
    val regex: Regex = """(\w+) -> (\w+)""".toRegex()
    fun part1(input: List<String>, times: Int): Long {
        var polymerTemplate = input
            .first()
            .map { it }
            .zipWithNext { previous, current -> "$previous$current" }
            .groupBy { it }
            .map { it.key to it.value.size.toLong() }
            .toMap()


        val pairInsertion = input
            .subList(2, input.size)
            .map {
                regex
                    .find(it)
                    ?.destructured
                    ?: error("Does not match")
            }
            .associate { (pair, letter) -> pair to PolymerPair(pair, letter) }

        for (i in 1..times) {
            polymerTemplate = polymerTemplate.entries
                .fold(mutableMapOf()) { acc, entry ->
                    val produces = pairInsertion[entry.key]!!.produce()
                    acc[produces.first] = entry.value + acc.getOrDefault(produces.first, 0L)
                    acc[produces.second] = entry.value + acc.getOrDefault(produces.second, 0L)
                    acc
                }
        }

        val result = polymerTemplate.entries
            .fold(mutableMapOf<Char, Long>()) { acc, entry ->
                acc[entry.key.first()] = entry.value + acc.getOrDefault(entry.key.first(), 0L)
                acc[entry.key.last()] = entry.value + acc.getOrDefault(entry.key.last(), 0L)
                acc
            }
            .map { it.key to ceil(it.value / 2.0).toLong() }
            .toMap()

        val most = result.maxOf { it.value }
        val least = result.minOf { it.value }

        return most - least
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput, 10) == 1588L)
    check(part1(testInput, 40) == 2188189693529L)

    val input = readInput("Day14")
    println(part1(input, 10))
    println(part1(input, 40))
}

private data class PolymerPair(val pair: String, val letter: String) {
    fun produce() = Pair("${pair.first()}$letter", "$letter${pair.last()}")
}
