fun main() {
    fun isPossible(design:String, towels:List<String>) : Boolean {
        if (design.isEmpty()) return true
        for (towel in towels) {
            if (design.startsWith(towel))
                if (isPossible(design.drop(towel.length), towels))
                    return true
        }
        return false
    }
    val cache = mutableMapOf("" to 1L)
    fun nbPossible(design:String, towels:List<String>) : Long {
        cache[design]?.also { return it }
        var count = 0L
        for (towel in towels) {
            if (design.startsWith(towel))
                count += nbPossible(design.drop(towel.length), towels)
        }
        cache[design] = count
        return count
    }
    fun part1(input: List<String>): Int {
        val towels = input[0].split(", ")
        var count = 0
        for(design in input.drop(2)) {
            if (isPossible(design, towels))
                count++
        }
        return count
    }

    fun part2(input: List<String>): Long {
        val towels = input[0].split(", ")
        var count = 0L
        cache.clear()
        cache[""] = 1L
        for(design in input.drop(2)) {
            count += nbPossible(design, towels)
        }
        return count
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day19_test")
    check(part1(testInput) == 6)
    check(part2(testInput) == 16L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
    // not 719644217597148
}
