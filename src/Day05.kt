fun main() {
    val regex: Regex = """(\d+),(\d+)\s->\s(\d+),(\d+)""".toRegex()
    fun createVentLines(input: List<String>) = input
        .map {
            regex
                .find(it)
                ?.destructured
                ?: error("Does not match")
        }
        .map { (x1, y1, x2, y2) -> Pair(Pair(x1.toInt(), y1.toInt()), Pair(x2.toInt(), y2.toInt())) }

    fun createVentMap(ventLines: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): VentMap {
        val maxX = ventLines.maxOf { maxOf(it.first.first, it.second.first) }
        val maxY = ventLines.maxOf { maxOf(it.first.second, it.second.second) }
        return VentMap(maxX, maxY)
    }

    fun part1(input: List<String>): Int {
        val ventLines = createVentLines(input)
        val ventMap = createVentMap(ventLines)
        ventLines.forEach { ventMap.drawStraightLine(it) }
        //println(ventMap.showField())

        return ventMap.countDangerSpots()
    }


    fun part2(input: List<String>): Int {
        val ventLines = createVentLines(input)
        val ventMap = createVentMap(ventLines)
        ventLines.forEach { ventMap.drawAllDirectionLine(it) }
        //println(ventMap.showField())

        return ventMap.countDangerSpots()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private data class VentMap(val maxX: Int, val maxY: Int) {
    var field = Array(maxY + 1) { Array(maxX + 1) { 0 } }

    fun showField() {
        field.forEach {
            it.forEach { location ->
                print(
                    when (location) {
                        0 -> "."
                        else -> location
                    }
                )
            }
            println()
        }
    }

    fun drawStraightLine(line: Pair<Pair<Int, Int>, Pair<Int, Int>>): Boolean {
        val xValues = Pair(line.first.first, line.second.first)
        val yValues = Pair(line.first.second, line.second.second)
        if (xValues.first == xValues.second) {
            for (y in rangeFix(yValues)) {
                field[y][xValues.first]++
            }
            return true
        }

        if (yValues.first == yValues.second) {
            for (x in rangeFix(xValues)) {
                field[yValues.first][x]++
            }
            return true
        }
        return false
    }

    fun drawAllDirectionLine(line: Pair<Pair<Int, Int>, Pair<Int, Int>>) {
        val xValues = Pair(line.first.first, line.second.first)
        val yValues = Pair(line.first.second, line.second.second)
        if (!drawStraightLine(line)) {
            val yIterator = rangeFix(yValues).iterator()
            val xIterator = rangeFix(xValues).iterator()

            while (yIterator.hasNext() && xIterator.hasNext()) {
                field[yIterator.next()][xIterator.next()]++
            }
        }
    }

    fun countDangerSpots() = field.flatten().count { it >= 2 }

    private fun rangeFix(range: Pair<Int, Int>) = when (range.first < range.second) {
        true -> range.first..range.second
        false -> range.first downTo range.second
    }
}
