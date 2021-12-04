import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Reads all lines from the given input txt file.
 */
fun readInputAsText(name: String) = File("src", "$name.txt").readText()

/**
 * Reads lines from the given input txt file and return them as Int.
 */
fun readInputAsInt(name: String) = File("src", "$name.txt").readLines().map { it.toInt() }

/**
 * Reads lines from the given input txt file and return each char as Int.
 */
fun readInputAsEachCharToInt(name: String) = File("src", "$name.txt").readLines()
    .map { it.toCharArray().map { it.toString().toInt() } }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Add two Pairs together
 */
operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = Pair(this.first + other.first, this.second + other.second)

/**
 * Add two Triples together
 */
operator fun Triple<Int, Int, Int>.plus(other: Triple<Int, Int, Int>) = Triple(this.first + other.first, this.second + other.second, this.third + other.third)
