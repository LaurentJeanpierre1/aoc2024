fun main() {
    var increasing : Boolean?
    fun checkLine(levels: List<String>): Boolean {
        increasing = null
        var safe = true
        levels.windowed(2).forEach { pair ->
            val prev = pair[0].toInt()
            val next = pair[1].toInt()
            if (prev > next) {
                if (increasing == true)
                    safe = false
                else {
                    increasing = false
                    if ((prev - next) !in 1..3)
                        safe = false
                }
            } else if (prev == next) {
                safe = false
            } else {
                if (increasing == false)
                    safe = false
                else {
                    increasing = true
                    if ((next - prev) !in 1..3)
                        safe = false
                }
            }
        }
        return safe
    }

    fun part1(input: List<String>): Int {
        var count = 0
        input.forEach { line ->
            val safe = checkLine(line.split(" "))
            if (safe) count++
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        input.forEach { line ->
            val levels = line.split(" ")
            var safe = checkLine(levels)
            if (!safe) {
                for( idx in levels.indices) {
                    val newLevels = MutableList(levels.size-1) {
                        return@MutableList levels[ if (it < idx) it else it+1 ]
                    }
                    if (checkLine(newLevels)) {
                        safe = true
                        break
                    }
                }
            }
            if (safe) count++
        }
        return count
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
