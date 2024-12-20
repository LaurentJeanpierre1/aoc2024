import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val map = input.map { line->line.toCharArray() }.toTypedArray()
        var startX = 0
        var startY = 0
        var endX = 0
        var endY = 0
        val dist = Array(map.size) { line ->
            IntArray(map[line].size) { col ->
                when(map[line][col]) {
                    '#' -> -1
                    'S' -> {
                        startX = col
                        startY = line
                        Int.MAX_VALUE
                    }
                    'E' -> {
                        endX = col
                        endY = line
                        0
                    }
                    else -> Int.MAX_VALUE
                }
            }
        }
        var x = endX
        var y = endY
        var nbStep = 0
        while (x != startX || y != startY) {
            if (dist[y-1][x] > nbStep) y--
            else if (dist[y+1][x] > nbStep) y++
            else if (dist[y][x-1] > nbStep) x--
            else if (dist[y][x+1] > nbStep) x++
            dist[y][x] = ++nbStep
        }
        var cheatsR = 0
        var cheatsL = 0
        var cheatsT = 0
        var cheatsB = 0
        var cheats100 = 0
        for (py in 1..<map.lastIndex) {
            for (px in 1..<map[py].lastIndex) {
                if (map[py][px] == '#' && map[py][px-1] != '#' && map[py][px+1] != '#') {
                    val cheat = abs(dist[py][px + 1] - dist[py][px - 1])
                    if (dist[py][px + 1] < dist[py][px - 1] - 2) {
                        cheatsR++
                        if (cheat >= 102)
                            cheats100++
                    }
                    if (dist[py][px - 1] < dist[py][px + 1] - 2) {
                        cheatsL++
                        if (cheat >= 102)
                            cheats100++
                    }
                }
            }
        }
        for (px in 1..<map[0].lastIndex) {
            for (py in 1..<map.lastIndex) {
                if (map[py][px] == '#' && map[py-1][px] != '#' && map[py+1][px] != '#') {
                    val cheat = abs(dist[py+1][px] - dist[py-1][px])
                    if (dist[py+1][px] < dist[py-1][px] - 2) {
                        cheatsB++
                        if (cheat >= 102)
                            cheats100++
                    }
                    if (dist[py-1][px] < dist[py+1][px] - 2) {
                        cheatsT++
                        if (cheat >= 102)
                            cheats100++
                    }
                }
            }
        }
        return cheats100
    }
    data class State(val x:Int, val y:Int) {
        var distance : Int = 0

        constructor(x: Int, y: Int, dist: Int) : this(x,y) {
            distance = dist
        }
    }

    fun part2(input: List<String>, target: Int): Long {
        val map = input.map { line->line.toCharArray() }.toTypedArray()
        var startX = 0
        var startY = 0
        var endX = 0
        var endY = 0
        val dist = Array(map.size) { line ->
            IntArray(map[line].size) { col ->
                when(map[line][col]) {
                    '#' -> -1
                    'S' -> {
                        startX = col
                        startY = line
                        Int.MAX_VALUE
                    }
                    'E' -> {
                        endX = col
                        endY = line
                        0
                    }
                    else -> Int.MAX_VALUE
                }
            }
        }
        var x = endX
        var y = endY
        var nbStep = 0
        val path = mutableListOf(State(x,y,0))
        while (x != startX || y != startY) {
            if (dist[y-1][x] > nbStep) y--
            else if (dist[y+1][x] > nbStep) y++
            else if (dist[y][x-1] > nbStep) x--
            else if (dist[y][x+1] > nbStep) x++
            dist[y][x] = ++nbStep
            path += State(x,y,nbStep)
        }

        val pathIdx = path.toTypedArray()
        var nbCheats = 0L
        for (idx in pathIdx.indices) {
            val end = pathIdx[idx]
            for (idx2 in idx+target..pathIdx.lastIndex) {
                val dx = abs(pathIdx[idx2].x - end.x)
                val dy = abs(pathIdx[idx2].y - end.y)
                if (dx+dy <= 20) {
                    val cheat = pathIdx[idx2].distance - end.distance - dx - dy
                    if (cheat >= target) {
                        nbCheats++
                    }
                }
            }
        }
        return nbCheats
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day20_test")
    check(part1(testInput) == 0)
    check(part2(testInput, 76) == 3L)
    check(part2(testInput, 74) == 7L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day20")
    part1(input).println()
    part2(input, 100).println()
}
