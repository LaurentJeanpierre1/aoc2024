fun main() {
    fun count1(line: Int, col: Int, progress: Char, scores: MutableList<MutableList<Set<Coord>?>>, map: List<CharArray>): Set<Coord> {
        if (scores[line][col] != null) return scores[line][col]!!
        if (progress == '9') {
            val res = setOf(Coord(col, line))
            scores[line][col] = res
            return res
        }
        val res = mutableSetOf<Coord>()
        if (line>0 && map[line-1][col] == progress+1)
            res += count1(line - 1, col, progress+1, scores, map)
        if (line<map.lastIndex && map[line+1][col] == progress+1)
            res += count1(line + 1, col, progress+1, scores, map)
        if (col>0 && map[line][col-1] == progress+1)
            res += count1(line, col - 1, progress+1, scores, map)
        if (col<map[0].lastIndex && map[line][col+1] == progress+1)
            res += count1(line, col + 1, progress+1, scores, map)
        scores[line][col] = res
        return res
    }

    fun count2(line: Int, col: Int, progress: Char, scores: MutableList<MutableList<Int>>, map: List<CharArray>): Int {
        if (scores[line][col] != -1) return scores[line][col]
        if (progress == '9') {
            scores[line][col] = 1
            return 1
        }
        var res = 0
        if (line>0 && map[line-1][col] == progress+1)
            res += count2(line - 1, col, progress+1, scores, map)
        if (line<map.lastIndex && map[line+1][col] == progress+1)
            res += count2(line + 1, col, progress+1, scores, map)
        if (col>0 && map[line][col-1] == progress+1)
            res += count2(line, col - 1, progress+1, scores, map)
        if (col<map[0].lastIndex && map[line][col+1] == progress+1)
            res += count2(line, col + 1, progress+1, scores, map)
        scores[line][col] = res
        return res
    }

    fun part1(input: List<String>): Int {
        val map = input.map { it.toCharArray() }.toList()
        val scores = MutableList(map.size) {
            MutableList<Set<Coord>?>(map[0].size) { null }
        }
        var sum = 0
        for (line in map.withIndex())
            for (col in line.value.withIndex())
                if (col.value == '0') {
                    val s = count1(line.index, col.index, '0', scores, map)
                    sum += s.size
                }
        return sum
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.toCharArray() }.toList()
        val scores = MutableList(map.size) {
            MutableList(map[0].size) { -1 }
        }
        var sum = 0
        for (line in map.withIndex())
            for (col in line.value.withIndex())
                if (col.value == '0') {
                    val s = count2(line.index, col.index, '0', scores, map)
                    sum += s
                }
        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
