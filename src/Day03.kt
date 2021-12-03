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

    fun convertToPairs(input: List<Int>) = input
        .fold(Pair(0, 0)) { acc, cur ->
            acc + when (cur) {
                0 -> Pair(1, 0)
                1 -> Pair(0, 1)
                else -> Pair(0, 0)
            }
        }

    fun convertToNumbers(input: Boolean) = when (input) {
        true -> 1
        false -> 0
    }

    fun convertToDecimal(input: List<Boolean>) = input
        .reversed()
        .let { binary ->
            var first = 0
            var second = 0
            binary.forEachIndexed { index, element ->
                if (element) {
                    first = (first + 1 * 2.0.pow(index)).toInt()
                } else {
                    second = (second + 1 * 2.0.pow(index)).toInt()
                }
            }
            Pair(first, second)
        }


    fun part1(input: List<List<Int>>) = transpose(input)
        .map { convertToPairs(it) }
        .map { it.first < it.second }
        .let { binary -> convertToDecimal(binary) }
        .let { pair -> pair.first * pair.second }


    fun part2(input: List<List<Int>>): Int {
        val max = input.maxOf { it.size }
        var oxygenInput = input
        var scrubberInput = input
        for (index in 0 until max) {
            val transposeOxygenInput = transpose(oxygenInput)
                .map { convertToPairs(it) }
                .map { it.first <= it.second }

            val oxygenFilterValue = convertToNumbers(transposeOxygenInput[index])
            if (oxygenInput.size > 1) {
                oxygenInput = oxygenInput.filter { it[index] == oxygenFilterValue }
            }

            val transposeScrubberInput = transpose(scrubberInput)
                .map { convertToPairs(it) }
                .map { it.first > it.second }

            val scrubberFilterValue = convertToNumbers(transposeScrubberInput[index])
            if (scrubberInput.size > 1) {
                scrubberInput = scrubberInput.filter { it[index] == scrubberFilterValue }
            }
        }

        val first = oxygenInput[0].reversed().foldIndexed(0) { index, acc, element ->
            (acc + element * 2.0.pow(index)).toInt()
        }
        val second = scrubberInput[0].reversed().foldIndexed(0) { index, acc, element ->
            (acc + element * 2.0.pow(index)).toInt()
        }

        return first * second
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsEachCharToInt("Day03_test")

    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInputAsEachCharToInt("Day03")
    println(part1(input))
    println(part2(input))
}
