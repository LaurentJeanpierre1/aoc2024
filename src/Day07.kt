fun main() {
    fun concat(v1: Long, v2: Long): Long {
        return (v1.toString() + v2.toString()).toLong()
    }

    fun solvable(nbs: List<Long>, idx: Int, currentValue: Long, part2: Boolean): Boolean {
        if (idx >= nbs.size)
            return currentValue == nbs[0]
        return solvable(nbs, idx+1, currentValue + nbs[idx], part2) ||
                solvable(nbs, idx+1, currentValue * nbs[idx], part2) ||
                (part2 && solvable(nbs, idx+1, concat(currentValue, nbs[idx]), true))
    }

    fun part1(input: List<String>): Long {
        val exp = Regex("(\\d+)[ :]*")
        var sum = 0L
        for (line in input) {
            val nbs = exp.findAll(line).map { it.groupValues[1].toLong() }.toList()
            if (solvable(nbs, 2, nbs[1], false))
                sum += nbs[0]
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val exp = Regex("(\\d+)[ :]*")
        var sum = 0L
        for (line in input) {
            val nbs = exp.findAll(line).map { it.groupValues[1].toLong() }.toList()
            if (solvable(nbs, 2, nbs[1], true))
                sum += nbs[0]
        }
        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
