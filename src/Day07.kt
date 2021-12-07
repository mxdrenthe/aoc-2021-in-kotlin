fun main() {
    fun convertInput(input: List<String>) = input
        .flatMap {
            it.split(",")
                .map { it.toInt() }
        }

    fun part1(input: List<String>): Long {
        val crabs = convertInput(input)
        val min = crabs.minOf { it }.toLong()
        val max = crabs.maxOf { it }.toLong()

        var fuel = Long.MAX_VALUE
        for (position in min..max) {
            val neededFuel = crabs.sumOf {
                when (it < position) {
                    true -> position - it
                    false -> it - position
                }
            }
            if (neededFuel < fuel) {
                fuel = neededFuel
            }
        }
        return fuel
    }

    fun part2(input: List<String>): Long {
        val crabs = convertInput(input)
        val min = crabs.minOf { it }.toLong()
        val max = crabs.maxOf { it }.toLong()

        var fuel = Long.MAX_VALUE
        for (position in min..max) {
            val neededFuel = crabs.sumOf {
                when (it > position) {
                    true -> (position .. it)
                        .foldIndexed(0L) { index, acc, _ -> acc + index }
                    false -> (it .. position)
                        .foldIndexed(0L) { index, acc, _ -> acc + index}
                }
            }
            if (neededFuel < fuel) {
                fuel = neededFuel
            }
        }
        return fuel
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37L)
    check(part2(testInput) == 168L)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
