fun main() {
    val regex: Regex = """(\w+)\s(\d+)""".toRegex()

    fun part1(input: List<String>) = input
        .map {
            regex
                .find(it)
                ?.destructured
                ?: error("Does not match")
        }.fold(Pair(0, 0)) { acc, (direction, unit) ->
            val units = unit.toInt()
            acc + when (direction) {
                "forward" -> Pair(units, 0)
                "up" -> Pair(0, -units)
                "down" -> Pair(0, units)
                else -> Pair(0, 0)
            }
        }.let { pair -> pair.first * pair.second }


    fun part2(input: List<String>) = input
        .map {
            regex
                .find(it)
                ?.destructured
                ?: error("Does not match")
        }.fold(Triple(0, 0, 0)) { acc, (direction, unit) ->
            val units = unit.toInt()
            acc + when (direction) {
                "forward" -> Triple(units, acc.third * units, 0)
                "up" -> Triple(0, 0, -units)
                "down" -> Triple(0, 0, units)
                else -> Triple(0, 0, 0)
            }
        }.let { triple -> triple.first * triple.second }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
