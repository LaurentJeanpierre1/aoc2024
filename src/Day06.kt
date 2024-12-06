enum class Dir {
  UP, RIGHT, DOWN, LEFT
}

data class OrientedLoc (val posX: Int, val posY: Int, val dir: Dir)
data class Loc (val posX: Int, val posY: Int)

fun main() {

    fun part1(input: List<String>): Int {
        val map = input.map { it.toCharArray() }
        var posX = -1
        var posY = -1
        var dir = Dir.UP
        for (line in map.withIndex()) {
            val col = line.value.indexOf('^')
            if (col != -1) {
                posX = col
                posY = line.index
            }
        }
        var lastPosX = posX
        var lastPosY = posY
        while (posX in map[0].indices && posY in map.indices) {
            if (map[posY][posX] == '#') {
                posX = lastPosX
                posY = lastPosY
                dir = when(dir) {
                    Dir.UP -> Dir.RIGHT
                    Dir.RIGHT -> Dir.DOWN
                    Dir.DOWN -> Dir.LEFT
                    Dir.LEFT -> Dir.UP
                }
            } else {
                map[posY][posX] = 'X'
                lastPosY = posY
                lastPosX = posX
                when (dir) {
                    Dir.UP -> posY--
                    Dir.RIGHT-> posX++
                    Dir.DOWN -> posY++
                    Dir.LEFT -> posX--
                }
            }
        }
        return map.sumOf { line -> line.count { it=='X' } }
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.toCharArray() }
        var posX = -1
        var posY = -1
        var dir = Dir.UP
        for (line in map.withIndex()) {
            val col = line.value.indexOf('^')
            if (col != -1) {
                posX = col
                posY = line.index
            }
        }
        val firstPosX = posX
        val firstPosY = posY
        var lastPosX = posX
        var lastPosY = posY
        val locs = mutableSetOf(Loc(posX, posY))
        while (posX in map[0].indices && posY in map.indices) {
            if (map[posY][posX] == '#') {
                posX = lastPosX
                posY = lastPosY
                dir = when(dir) {
                    Dir.UP -> Dir.RIGHT
                    Dir.RIGHT -> Dir.DOWN
                    Dir.DOWN -> Dir.LEFT
                    Dir.LEFT -> Dir.UP
                }
            } else {
                locs += Loc(posX, posY)
                lastPosY = posY
                lastPosX = posX
                when (dir) {
                    Dir.UP -> posY--
                    Dir.RIGHT-> posX++
                    Dir.DOWN -> posY++
                    Dir.LEFT -> posX--
                }
            }
        }
        locs.remove(Loc(firstPosX, firstPosY))
        val obstacles = mutableSetOf<Loc>()
        for (loc in locs) {
            map[loc.posY][loc.posX] = '#'
            posX = firstPosX
            posY = firstPosY
            dir = Dir.UP
            lastPosX = posX
            lastPosY = posY
            val path = mutableSetOf<OrientedLoc>()
            while (posX in map[0].indices && posY in map.indices) {
                if (map[posY][posX] == '#') {
                    posX = lastPosX
                    posY = lastPosY
                    dir = when(dir) {
                        Dir.UP -> Dir.RIGHT
                        Dir.RIGHT -> Dir.DOWN
                        Dir.DOWN -> Dir.LEFT
                        Dir.LEFT -> Dir.UP
                    }
                } else {
                    if (! path.add(OrientedLoc(posX, posY, dir))) {
                        // loop found
                        obstacles.add(loc)
                        break
                    }
                    lastPosY = posY
                    lastPosX = posX
                    when (dir) {
                        Dir.UP -> posY--
                        Dir.RIGHT-> posX++
                        Dir.DOWN -> posY++
                        Dir.LEFT -> posX--
                    }
                }
            }
            map[loc.posY][loc.posX] = '.'
        }
        obstacles.remove(Loc(firstPosX, firstPosY))
        return obstacles.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
