import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        for (line in input)
            line.split(Regex(" +")).run {
                left += this[0].toInt()
                right += this[1].toInt()
            }
        left.sort()
        right.sort()
        var sum=0
        for (idx in left.indices)
            sum += abs(left[idx] - right[idx])
        return sum
    }

    fun part2(input: List<String>): Int {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        for (line in input)
            line.split(Regex(" +")).run {
                left += this[0].toInt()
                right += this[1].toInt()
            }
        var sum=0
        for (value in left)
            sum += right.count { it == value } * value
        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
