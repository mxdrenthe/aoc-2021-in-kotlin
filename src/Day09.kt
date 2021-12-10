fun main() {
    fun part1(input: List<List<Int>>): Int {
        val lowPoints = mutableListOf<Int>()
        input.forEachIndexed { y, row ->
            val previousRow = input.getOrElse(y - 1) { emptyList() }
            val nextRow = input.getOrElse(y + 1) { emptyList() }
            row.forEachIndexed { x, current ->
                val topOfCurrent = previousRow.getOrElse(x) { 10 }
                val leftOfCurrent = row.getOrElse(x - 1) { 10 }
                val rightOfCurrent = row.getOrElse(x + 1) { 10 }
                val belowOfCurrent = nextRow.getOrElse(x) { 10 }
                if (current < topOfCurrent && current < leftOfCurrent && current < rightOfCurrent && current < belowOfCurrent) {
                    lowPoints.add(current)
                }
            }
        }

        return lowPoints.sumOf { it + 1 }
    }

    fun part2(input: List<List<Int>>) = 0

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsEachCharToInt("Day09_test")
    check(part1(testInput) == 15)
    //check(part2(testInput) == 1134)

    val input = readInputAsEachCharToInt("Day09")
    println(part1(input))
    println(part2(input))
}
