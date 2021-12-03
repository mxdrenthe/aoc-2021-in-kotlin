import kotlin.math.pow

fun main() {
    fun transpose(input: List<List<Int>>): List<List<Int>> {
        val max = input.maxOf { it.size }
        return with(input.map { it.subList(0, max) }) {
            this.first()
                .indices
                .map { i -> this.map { it[i] }.toList() }
        }
    }

    fun part1(input: List<List<Int>>) = transpose(input)
        .map {
            it.fold(Pair(0, 0)) { acc, cur ->
                acc + when (cur) {
                    0 -> Pair(1, 0)
                    1 -> Pair(0, 1)
                    else -> Pair(0, 0)
                }
            }
        }
        .map { it.first < it.second }
        .let { binary ->
            var gamma = 0
            var epsilon = 0
            binary.reversed().forEachIndexed { index, element ->
                if (element) {
                    gamma = (gamma + 1 * 2.0.pow(index.toDouble())).toInt()
                } else {
                    epsilon = (epsilon + 1 * 2.0.pow(index.toDouble())).toInt()
                }
            }
            gamma * epsilon
        }


    fun part2(input: List<List<Int>>): Int {
        return 0
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsEachCharToInt("Day03_test")

    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInputAsEachCharToInt("Day03")
    println(part1(input))
    println(part2(input))
}
