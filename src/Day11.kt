fun main() {
    fun printMap(map: Map<Pair<Int, Int>, Dumbo>) {
        val maxX = map.keys.maxOf { it.first }
        val maxY = map.keys.maxOf { it.second }

        for (y in 0..maxX) {
            for (x in 0..maxY) {
                print(map[Pair(x, y)]?.value)
            }
            println()
        }
        println()
    }

    fun part1(input: List<List<Int>>): Long {
        val map = input.flatMapIndexed { y, row -> row.mapIndexed { x, current -> Pair(x, y) to Dumbo(Pair(x, y), current) } }
            .toMap()

        for (i in 1..100) {
            map.values.onEach { it.increaseEnergyLevel(map) }
            map.values.onEach { it.flash() }
        }

        return map.values.sumOf { it.flashCounter() }
    }


    fun part2(input: List<List<Int>>) : Int {
        val map = input.flatMapIndexed { y, row -> row.mapIndexed { x, current -> Pair(x, y) to Dumbo(Pair(x, y), current) } }
            .toMap()

        for (i in 1..1000) {
            map.values.onEach { it.increaseEnergyLevel(map) }
            map.values.onEach { it.flash() }
            if(map.values.size == map.values.filter { it.value == 0 }.size) return i
        }

        return 0
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsEachCharToInt("Day11_test")
    check(part1(testInput) == 1656L)
    check(part2(testInput) == 195)

    val input = readInputAsEachCharToInt("Day11")
    println(part1(input))
    println(part2(input))
}

private data class Dumbo(val cords: Pair<Int, Int>, var value: Int) {
    var flashCounter = 0L

    fun flash() {
        if (value > 9) {
            value = 0
            flashCounter++
        }
    }

    private fun updateNeighbors(map: Map<Pair<Int, Int>, Dumbo>) {
        val dummy = Dumbo(Pair(-1, -1), 20)
        map.getOrElse(Pair(cords.first - 1, cords.second - 1)) { dummy }.let { it.increaseEnergyLevel(map) }
        map.getOrElse(Pair(cords.first, cords.second - 1)) { dummy }.let { it.increaseEnergyLevel(map) }
        map.getOrElse(Pair(cords.first + 1, cords.second - 1)) { dummy }.let { it.increaseEnergyLevel(map) }
        map.getOrElse(Pair(cords.first - 1, cords.second)) { dummy }.let { it.increaseEnergyLevel(map) }
        map.getOrElse(Pair(cords.first + 1, cords.second)) { dummy }.let { it.increaseEnergyLevel(map) }
        map.getOrElse(Pair(cords.first - 1, cords.second + 1)) { dummy }.let { it.increaseEnergyLevel(map) }
        map.getOrElse(Pair(cords.first, cords.second + 1)) { dummy }.let { it.increaseEnergyLevel(map) }
        map.getOrElse(Pair(cords.first + 1, cords.second + 1)) { dummy }.let { it.increaseEnergyLevel(map) }
    }

    fun flashCounter() = flashCounter

    fun increaseEnergyLevel(map: Map<Pair<Int, Int>, Dumbo>) {
        value++
        if (value == 10) {
            updateNeighbors(map)
        }
    }
}

