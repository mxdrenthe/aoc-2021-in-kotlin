fun main() {
    val points: Regex = """(\d+),(\d+)""".toRegex()
    val folds: Regex = """([xy])=(\d+)""".toRegex()

    fun convertCords(input: List<String>) = input
        .mapNotNull {
            points
                .find(it)
                ?.destructured
        }
        .map { (x, y) -> Pair(x.toInt(), y.toInt()) }
        .toSet()

    fun convertFolds(input: List<String>) = input
        .mapNotNull {
            folds
                .find(it)
                ?.destructured
        }
        .map { (axel, time) -> Pair(axel, time.toInt()) }

    fun foldMap(cords: Set<Pair<Int, Int>>, fold: Pair<String, Int>): Set<Pair<Int, Int>> {
        if (fold.first == "x") {
            return cords.map { (x, y) ->
                if (x < fold.second) {
                    Pair(x, y)
                } else {
                    val newX = fold.second - (x - fold.second)
                    Pair(newX, y)
                }
            }.toSet()
        } else {
            return cords.map { (x, y) ->
                if (y < fold.second) {
                    Pair(x, y)
                } else {
                    val newY = fold.second - (y - fold.second)
                    Pair(x, newY)
                }
            }.toSet()
        }
    }

    fun printMap(cords: Set<Pair<Int, Int>>) {
        val maxX = cords.maxOf { it.first }
        val maxY = cords.maxOf { it.second }
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val cord = Pair(x, y)
                if (cords.contains(cord)) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        var cords = convertCords(input)
        val fold = convertFolds(input).first()
        cords = foldMap(cords,fold)
        return cords.size
    }

    fun part2(input: List<String>): Int {
        var cords = convertCords(input)
        convertFolds(input)
            .forEach{cords = foldMap(cords,it)}
        printMap(cords)
        return cords.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    check(part2(testInput) == 16)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
