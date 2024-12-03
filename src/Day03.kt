fun main() {
    fun part1(input: List<String>): Int {
        val valids = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        return valids.findAll(input[0]).sumOf { match-> match.groupValues[1].toInt() * match.groupValues[2].toInt() }
    }

    fun part2(input: List<String>): Int {
        val valids = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)")
        var enabled = true
        var sum = 0
        valids.findAll(input[0]).forEach {
            when(it.groupValues[0].substring(0..3)) {
                "do()" -> enabled = true
                "don'" -> enabled = false
                "mul(" -> if (enabled) sum += it.groupValues[1].toInt() * it.groupValues[2].toInt()
            }
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")) == 161)
    check(part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
