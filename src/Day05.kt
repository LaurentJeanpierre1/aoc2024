
fun main() {
    fun part1(input: List<String>): Int {
        val after = mutableMapOf<Int, MutableSet<Int>>()
        val ite = input.listIterator()
        while (ite.hasNext()) {
            val line = ite.next()
            if (line.isBlank()) break
            val parts = line.split("|").map { it.toInt() }
            after.compute(parts[1]) { _, list ->
                list?.also { it.add(parts[0]) } ?: mutableSetOf(parts[0])
            }
        }
        var sum = 0
        while (ite.hasNext()) {
            val line = ite.next().split(",").map { it.toInt() }
            val iteLine = line.withIndex().iterator()
            var ok = true
            while (ok && iteLine.hasNext()) {
                val elt = iteLine.next()
                val iteNext = line.listIterator(elt.index+1)
                val shouldBeAfter = after[elt.value]
                for (next in iteNext) {
                    if (shouldBeAfter?.contains(next) == true)
                        ok = false
                }
            }
            if (ok)
                sum += line[line.size / 2]
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val after = mutableMapOf<Int, MutableSet<Int>>()
        val ite = input.listIterator()
        while (ite.hasNext()) {
            val line = ite.next()
            if (line.isBlank()) break
            val parts = line.split("|").map { it.toInt() }
            after.compute(parts[1]) { _, list ->
                list?.also { it.add(parts[0]) } ?: mutableSetOf(parts[0])
            }
        }
        var sum = 0
        while (ite.hasNext()) {
            val line = ite.next().split(",").map { it.toInt() }
            val iteLine = line.withIndex().iterator()
            var ok = true
            while (ok && iteLine.hasNext()) {
                val elt = iteLine.next()
                val iteNext = line.listIterator(elt.index+1)
                val shouldBeAfter = after[elt.value]
                for (next in iteNext) {
                    if (shouldBeAfter?.contains(next) == true)
                        ok = false
                }
            }
            if (!ok) {
                val newLine = line.sortedWith {
                    v1,v2 -> if (after[v1]?.contains(v2)==true) 1 else -1
                }
                sum += newLine[newLine.size / 2]
            }
        }
        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
