fun main() {
    val regex: Regex = """(\w+)-(\w+)""".toRegex()
    fun createMap(input: List<String>) = input.map {
        regex
            .find(it)
            ?.destructured
            ?: error("Does not match")
    }.fold(mutableMapOf<String, Cave>()) { map, (caveName1, caveName2) ->
        val cave1 = map.getOrPut(caveName1) { Cave(caveName1) }
        val cave2 = map.getOrPut(caveName2) { Cave(caveName2) }
        cave1.addPath(cave2)
        map
    }

    fun part1(input: List<String>): Int {
        val graph = createMap(input)
        graph["start"]!!.walker(mutableListOf())
        return graph["end"]!!.counter
    }

    fun part2(input: List<String>): Int {
        val graph = createMap(input)
        graph["start"]!!.walker2(mutableListOf())
        return graph["end"]!!.counter
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

private data class Cave(val name: String) {
    val paths: MutableSet<Cave> = mutableSetOf()
    val bigCave: Boolean = name == name.uppercase()
    var counter = 0
    fun addPath(cave: Cave) {
        if (!paths.contains(cave)) {
            paths.add(cave)
            cave.addPath(this)
        }
    }

    fun walker(takenPath: MutableList<String>) {
        if (takenPath.contains(name) && !bigCave) {
            return
        }
        takenPath.add(name)
        if ("end" == name) {
            counter++
            return
        }

        paths.forEach {
            it.walker(takenPath.toMutableList())
        }
    }

    fun walker2(takenPath: MutableList<String>) {
        if ("start" == name && takenPath.isNotEmpty()) {
            return
        }
        val timeVisit = takenPath
            .filter { it.lowercase() == it }
            .groupBy { it }
            .maxOfOrNull { it.value.size }
            .let { it ?: 0 }
        if ((timeVisit == 2 && takenPath.contains(name)) && !bigCave) {
            return
        }
        takenPath.add(name)
        if ("end" == name) {
            counter++
            return
        }

        paths.forEach {
            it.walker2(takenPath.toMutableList())
        }
    }

    override fun toString(): String {
        return "Cave(name='$name', bigCave=$bigCave)"
    }
}
