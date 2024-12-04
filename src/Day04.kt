fun main() {
    fun part1(input: List<String>): Int {
        val letters = input.map { line -> line.toCharArray() }
        val nbLines = letters.size
        var count = 0
        for (line in letters.withIndex()) {
            val lineSize = line.value.size
            for (col in line.value.withIndex()) {
                if (col.value == 'X') {
                    for (dirX in -1..1) {
                        if (dirX < 0 && col.index < 3) continue
                        if (dirX > 0 && col.index > lineSize-4) continue
                        for (dirY in -1..1) {
                            if (dirY < 0 && line.index < 3) continue
                            if (dirY > 0 && line.index > nbLines-4) continue
                            var ok = true
                            for (letter in "MAS".withIndex()) {
                                if (letters[line.index+(1+letter.index)*dirY][col.index+(1+letter.index)*dirX] != letter.value) {
                                    ok = false
                                    break
                                }
                            }
                            if (ok) count++
                        }
                    }
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val letters = input.map { line -> line.toCharArray() }
        val nbLines = letters.size
        var count = 0
        for (line in letters.withIndex()) {
            val lineSize = line.value.size
            if (line.index == 0) continue
            if (line.index == nbLines-1) continue
            for (col in line.value.withIndex()) {
                if (col.index == 0) continue
                if (col.index == lineSize-1) continue
                if (col.value == 'A') {
                    var xCount = 0
                    for (dX in -1..1) {
                        if (dX == 0) continue
                        for (dY in -1..1) {
                            if (dY == 0) continue
                            var ok = true
                            for (letter in "MAS".withIndex()) {
                                if (letters[line.index+dY*(letter.index-1)][col.index+dX*(letter.index-1)] != letter.value) {
                                    ok = false
                                    break
                                }
                            }
                            if (ok) xCount++
                        }
                    }
                    if (xCount >= 2) count++
                }
            }
        }
        return count
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
