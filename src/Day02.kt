private data class Submarine(val position: Pair<Int, Int> = Pair(0, 0), val aim: Int = 0)

fun main() {
    val regex: Regex = """(\w+)\s(\d+)""".toRegex()
    fun part1(input: List<String>): Int {
        return input
            .map {
                regex
                    .find(it)
                    ?.destructured
                    ?: error("Does not match")
            }.fold(Submarine(Pair(0, 0), 0)) { acc, (direction, unit) ->
                val units = unit.toInt()
                when (direction) {
                    "forward" -> Submarine(acc.position + Pair(units, 0), acc.aim)
                    "up" -> Submarine(acc.position + Pair(0, -units), acc.aim)
                    "down" -> Submarine(acc.position + Pair(0, units), acc.aim)
                    else -> Submarine(acc.position, acc.aim)
                }
            }.let { submarine -> submarine.position.first * submarine.position.second }
    }

    fun part2(input: List<String>): Int {
        return input
            .map {
                regex
                    .find(it)
                    ?.destructured
                    ?: error("Does not match")
            }.fold(Submarine(Pair(0, 0), 0)) { acc, (direction, unit) ->
                val units = unit.toInt()
                when (direction) {
                    "forward" -> Submarine(acc.position + Pair(units, acc.aim * units), acc.aim)
                    "up" -> Submarine(acc.position, acc.aim - units)
                    "down" -> Submarine(acc.position, acc.aim + units)
                    else -> Submarine(acc.position, acc.aim)
                }
            }.let { submarine -> submarine.position.first * submarine.position.second }
    }


    // test if implementation meets criteria from the description, like:

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
