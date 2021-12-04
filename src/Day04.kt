fun main() {
    fun createCards(inputSplit: List<String>) = inputSplit.subList(1, inputSplit.size)
        .map { it.split("\n") }
        .map { row ->
            row.map { numbers ->
                numbers.trim()
                    .split("""\s+""".toRegex())
                    .map { number -> BingoNumber(number.toInt(), false) }
            }
        }
        .mapIndexed() { index, lists -> Card(index, lists) }


    fun createSequence(inputSplit: List<String>) = inputSplit.first().split(",").map { it.toInt() }

    fun part1(input: String): Int {
        val inputSplit = input.trim().split("\n\n")
        val sequence = createSequence(inputSplit)
        val cards = createCards(inputSplit)

        var cardWithBingo: Card?
        for (draw in sequence) {
            cards.forEach { it.markNumber(draw) }
            cardWithBingo = cards.firstOrNull { it.hasBingo() }

            if (cardWithBingo != null) {
                return draw * cardWithBingo.sumUnmarkedNumbers()
            }
        }

        return 0
    }

    fun part2(input: String): Int {
        val inputSplit = input.trim().split("\n\n")
        val sequence = createSequence(inputSplit)
        var cards = createCards(inputSplit)

        for (draw in sequence) {
            cards.forEach { it.markNumber(draw) }
            when (cards.filter { !it.hasBingo() }.size) {
                0 -> return draw * cards.first().sumUnmarkedNumbers()
                else -> cards = cards.filter { !it.hasBingo() }
            }
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInputAsText("Day04")
    println(part1(input))
    println(part2(input))
}

private data class Card(val id: Int, val numbers: List<List<BingoNumber>>) {
    fun markNumber(number: Int) {
        numbers
            .flatten()
            .firstOrNull() { it.number == number }
            ?.marked = true
    }

    fun hasBingo() = hasRowBingo() || hasColumnBingo()

    fun sumUnmarkedNumbers() =
        numbers
            .flatten()
            .filter { !it.marked }
            .sumOf { it.number }

    private fun hasRowBingo() = numbers.any { row -> row.all { it.marked } }

    private fun hasColumnBingo() =
        with(numbers) {
            this.first()
                .indices
                .map { i -> this.map { it[i] }.toList() }
                .any { row -> row.all { it.marked } }
        }
}

private data class BingoNumber(val number: Int, var marked: Boolean)
