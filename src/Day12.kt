import java.util.Comparator

data class Region(var area: Int, var perimeter: Int)
class BulkRegion {
    var area = 1
    private var perimeterTop = mutableMapOf<Int, MutableList<IntRange>>()
    private var perimeterLeft = mutableMapOf<Int, MutableList<IntRange>>()
    private var perimeterRight = mutableMapOf<Int, MutableList<IntRange>>()
    private var perimeterBottom = mutableMapOf<Int, MutableList<IntRange>>()

    private fun addTo(perimeter: MutableMap<Int, MutableList<IntRange>>, x: Int, y: Int) =
        perimeter.compute(y) { _: Int, range: MutableList<IntRange>? ->
            if (range == null) mutableListOf(IntRange(x,x))
            else {
                var added = false
                for (xRange in range.withIndex())
                    if (xRange.value.first == x+1) {
                        range[xRange.index] = IntRange(x, xRange.value.last)
                        added = true
                        break
                    } else if (xRange.value.last == x-1) {
                        range[xRange.index] = IntRange(xRange.value.first, x)
                        added = true
                        break
                    }
                if (!added)
                    range += IntRange(x,x)
                range
            }
        }
    fun grow(map: Array<CharArray>, x: Int, y: Int) {
        val kind = map[y][x]
        map[y][x] = map[y][x] + 32
        if (y==0) {
            addTo(this.perimeterTop, x, y)
        } else if (map[y-1][x] == kind) {
            this.area ++
            grow(map, x, y-1)
        } else if (map[y-1][x] != kind + 32)
            addTo(this.perimeterTop, x, y)
        if (y==map.lastIndex) {
            addTo(this.perimeterBottom, x, y)
        } else if (map[y+1][x] == kind) {
            this.area ++
            grow(map, x, y+1)
        } else if (map[y+1][x] != kind + 32)
            addTo(this.perimeterBottom, x, y)

        if (x==0) {
            addTo(this.perimeterLeft, y, x)
        } else if (map[y][x-1] == kind) {
            this.area ++
            grow(map, x-1, y)
        } else if (map[y][x-1] != kind + 32)
            addTo(this.perimeterLeft, y, x)
        if (x==map[y].lastIndex) {
            addTo(this.perimeterRight, y, x)
        } else if (map[y][x+1] == kind) {
            this.area ++
            grow(map, x+1, y)
        } else if (map[y][x+1] != kind + 32)
            addTo(this.perimeterRight, y, x)
    }

    private fun countSides(fences : MutableMap<Int, MutableList<IntRange>>) : Int {
        var total = 0
        for (sets in fences) {
            val ranges = sets.value
            ranges.sortWith(Comparator.comparingInt { it.first })
            var last = -2
            for (range in ranges) {
                if (last < range.first - 1)
                    total++
                last = range.last
            }
        }
        return total
    }
    fun getPerimeter(): Int = countSides(perimeterTop) + countSides(perimeterLeft) + countSides(perimeterRight) + countSides(perimeterBottom)

}

fun main() {
    fun Region.grow(map: Array<CharArray>, x: Int, y: Int) {
        val kind = map[y][x]
        map[y][x] = map[y][x] + 32
        if (y==0) {
            this.perimeter ++
        } else if (map[y-1][x] == kind) {
            this.area ++
            grow(map, x, y-1)
        } else if (map[y-1][x] != kind + 32)
            this.perimeter ++
        if (y==map.lastIndex) {
            this.perimeter ++
        } else if (map[y+1][x] == kind) {
            this.area ++
            grow(map, x, y+1)
        } else if (map[y+1][x] != kind + 32)
            this.perimeter ++

        if (x==0) {
            this.perimeter ++
        } else if (map[y][x-1] == kind) {
            this.area ++
            grow(map, x-1, y)
        } else if (map[y][x-1] != kind + 32)
            this.perimeter ++
        if (x==map[y].lastIndex) {
            this.perimeter ++
        } else if (map[y][x+1] == kind) {
            this.area ++
            grow(map, x+1, y)
        } else if (map[y][x+1] != kind + 32)
            this.perimeter ++
    }

    fun part1(input: List<String>): Int {
        val map = input.map { it.toCharArray() }.toTypedArray()
        var total = 0
        for (y in map.indices)
            for (x in map[y].indices) {
                if (map[y][x].code and 32 == 0) {
                    val area = Region(1,0)
                    area.grow(map,x,y)
                    total += area.area * area.perimeter
                }
            }
        return total
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.toCharArray() }.toTypedArray()
        var total = 0
        for (y in map.indices)
            for (x in map[y].indices) {
                if (map[y][x].code and 32 == 0) {
                    val area = BulkRegion()
                    area.grow(map,x,y)
                    total += area.area * area.getPerimeter()
                }
            }
        return total
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 140)
    val testInput2 = readInput("Day12_test2")
    check(part1(testInput2) == 772)
    val testInput3 = readInput("Day12_test3")
    check(part1(testInput3) == 1930)
    check(part2(testInput) == 80)
    check(part2(testInput3) == 1206)
    val testInput4 = readInput("Day12_test4")
    check(part2(testInput4) == 236)
    val testInput5 = readInput("Day12_test5")
    check(part2(testInput5) == 368)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
