data class Coord(val x: Int, val y: Int)

fun main() {
    fun part1(input: List<String>): Int {
        val antennas = mutableMapOf<Char, MutableList<Coord>>()
        val frequencies = mutableSetOf<Char>()
        val rangeX = input[0].indices
        val rangeY = input.indices

        for (line in input.withIndex()) {
            for (col in line.value.withIndex()) {
                if (col.value != '.') {
                    antennas.compute(col.value) { v: Char, coords: MutableList<Coord>? ->
                        coords?.also { it += Coord(col.index, line.index) } ?: mutableListOf(Coord(col.index, line.index))
                    }
                    frequencies += col.value
                }
            }
        }

        val antiNodes = mutableSetOf<Coord>()
        for ((_, coords) in antennas) {
            for (ant in coords.withIndex()) {
                coords.listIterator(ant.index+1).forEachRemaining { ant2->
                    val deltaX = ant2.x - ant.value.x
                    val deltaY = ant2.y - ant.value.y
                    val node1 = Coord( ant.value.x - deltaX, ant.value.y - deltaY)
                    val node2 = Coord( ant2.x + deltaX, ant2.y + deltaY)
                    if (node1.x in rangeX && node1.y in rangeY)
                        antiNodes.add(node1)
                    if (node2.x in rangeX && node2.y in rangeY)
                        antiNodes.add(node2)
                }
            }
        }
        return antiNodes.size
    }

    fun part2(input: List<String>): Int {
        val antennas = mutableMapOf<Char, MutableList<Coord>>()
        val frequencies = mutableSetOf<Char>()
        val rangeX = input[0].indices
        val rangeY = input.indices

        for (line in input.withIndex()) {
            for (col in line.value.withIndex()) {
                if (col.value != '.') {
                    antennas.compute(col.value) { v: Char, coords: MutableList<Coord>? ->
                        coords?.also { it += Coord(col.index, line.index) } ?: mutableListOf(Coord(col.index, line.index))
                    }
                    frequencies += col.value
                }
            }
        }

        val antiNodes = mutableSetOf<Coord>()
        for ((_, coords) in antennas) {
            for (ant in coords.withIndex()) {
                coords.listIterator(ant.index+1).forEachRemaining { ant2->
                    val deltaX = ant2.x - ant.value.x
                    val deltaY = ant2.y - ant.value.y
                    var count = 1
                    do {
                        val node1 = Coord( ant2.x - count*deltaX, ant2.y - count*deltaY)
                        if (node1.x in rangeX && node1.y in rangeY)
                            antiNodes.add(node1)
                        else
                            break
                        count++
                    } while (true)
                    count = 1
                    do {
                        val node2 = Coord( ant.value.x + count*deltaX, ant.value.y + count*deltaY)
                        if (node2.x in rangeX && node2.y in rangeY)
                            antiNodes.add(node2)
                        else
                            break
                        count++
                    } while (true)
                }
            }
        }
        return antiNodes.size    }


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
