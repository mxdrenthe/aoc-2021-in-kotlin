fun main() {
    fun checkSizeCave(map: Map<Pair<Int, Int>, CavePoint>, cords: Pair<Int, Int>, caves: MutableMap<Int, MutableList<Pair<Int, Int>>>, cavedId: Int) {
        val value = map.getOrElse(cords) { return }
        if (value.value == 9 || value.marked) return

        value.marked = true
        caves.getOrPut(cavedId) { mutableListOf() }.add(cords)
        checkSizeCave(map, Pair(cords.first + 1, cords.second), caves, cavedId)
        checkSizeCave(map, Pair(cords.first - 1, cords.second), caves, cavedId)
        checkSizeCave(map, Pair(cords.first, cords.second + 1), caves, cavedId)
        checkSizeCave(map, Pair(cords.first, cords.second - 1), caves, cavedId)
    }

    fun part1(input: List<List<Int>>): Int {
        val lowPoints = mutableListOf<Int>()
        input.forEachIndexed { y, row ->
            val previousRow = input.getOrElse(y - 1) { emptyList() }
            val nextRow = input.getOrElse(y + 1) { emptyList() }
            row.forEachIndexed { x, current ->
                val topOfCurrent = previousRow.getOrElse(x) { 9 }
                val leftOfCurrent = row.getOrElse(x - 1) { 9 }
                val rightOfCurrent = row.getOrElse(x + 1) { 9 }
                val belowOfCurrent = nextRow.getOrElse(x) { 9 }
                if (current < topOfCurrent && current < leftOfCurrent && current < rightOfCurrent && current < belowOfCurrent) {
                    lowPoints.add(current)
                }
            }
        }

        return lowPoints.sumOf { it + 1 }
    }

    fun part2(input: List<List<Int>>): Int {
        val map = input.flatMapIndexed { y, row -> row.mapIndexed { x, current -> Pair(x, y) to CavePoint(current, false) } }
            .toMap()

        val caves = emptyMap<Int, MutableList<Pair<Int, Int>>>().toMutableMap()
        val maxX = map.keys.maxOf { it.first }
        val maxY = map.keys.maxOf { it.second }

        for (x in 0..maxX) {
            for (y in 0..maxY) {
                checkSizeCave(map, Pair(x, y), caves, caves.size)
            }
        }

        return caves.values.map { it.size }
            .sortedBy { it }
            .reversed()
            .take(3)
            .fold(1) { acc, i -> acc * i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsEachCharToInt("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInputAsEachCharToInt("Day09")
    println(part1(input))
    println(part2(input))
}

private data class CavePoint(val value: Int, var marked: Boolean)
