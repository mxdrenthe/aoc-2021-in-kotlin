import kotlin.math.pow

fun main() {
    fun decipherSixLengthNumer(foundNumbers: MutableMap<Int, String>, cipherAsSet: Set<Char>, cipher: String) {
        if (cipherAsSet.containsAll(foundNumbers.getValue(7).toSet())) {
            if (cipherAsSet.containsAll(foundNumbers.getValue(4).toSet())) {
                foundNumbers[9] = cipher
            } else {
                foundNumbers[0] = cipher
            }
        } else {
            foundNumbers[6] = cipher
        }
    }

    fun decipherFiveLengthNumer(foundNumbers: MutableMap<Int, String>, cipherAsSet: Set<Char>, cipher: String) {
        if (foundNumbers.getValue(6).count { cipherAsSet.contains(it) } == 5) {
            foundNumbers[5] = cipher
        } else {
            if (cipherAsSet.containsAll(foundNumbers.getValue(7).toSet())) {
                foundNumbers[3] = cipher
            } else {
                foundNumbers[2] = cipher
            }
        }
    }

    fun decipherHashToNumber(ciphers: List<String>): Map<String, Int> {
        val uniqueNumbers = mapOf(2 to 1, 3 to 7, 4 to 4, 7 to 8)
        val foundNumbers = mutableMapOf<Int, String>()

        // Find 1, 4, 7, 8
        ciphers
            .filter { uniqueNumbers.containsKey(it.length) }
            .forEach { foundNumbers[uniqueNumbers.getValue(it.length)] = it }

        // Find 2, 3, 5
        ciphers
            .filter { it.length == 6 }
            .forEach {
                val cipherAsSet = it.toSet()
                decipherSixLengthNumer(foundNumbers, cipherAsSet, it)
            }

        // Find 0, 6, 9
        ciphers
            .filter { it.length == 5 }
            .forEach {
                val cipherAsSet = it.toSet()
                decipherFiveLengthNumer(foundNumbers, cipherAsSet, it)
            }


        return foundNumbers
            .toSortedMap()
            .map { it.value.toCharArray().also { it.sort() }.concatToString() to it.key }
            .toMap()
    }

    fun part1(input: List<String>) =
        input
            .map { it.substringAfter("|").trim() }
            .flatMap { it.split(" ") }
            .count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }


    fun part2(input: List<String>) = input
        .map { it.substringBefore("|").trim().split(" ") to it.substringAfter("|").trim().split(" ") }
        .map { decipherHashToNumber(it.first) to it.second }
        .map {
            it.second.foldIndexed(0) { index, acc, s ->
                val sortedValue = s.toCharArray().also { it.sort() }.concatToString()
                acc + (it.first
                    .getValue(sortedValue) * 10.0
                    .pow(3 - index))
                    .toInt()
            }
        }
        .sumOf { it }


// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
