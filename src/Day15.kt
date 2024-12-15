fun main() {
    fun part1(input: List<String>): Int {
        val map = input.map { it.toCharArray() }.toTypedArray()
        var posX = 0
        var posY = 0
        for (y in map.indices) {
            posX = map[y].indexOf('@')
            if (posX != -1) {
                posY = y
                break
            }
        }
        var height = posY+1
        while (map[height].isNotEmpty()) height++
        val orders = map.sliceArray(height+1..map.lastIndex)
        val crates = map.sliceArray(0..< height)
        //crates.forEach { it.asList().println() }

        for(line in orders)
            for(order in line) {
                //println("\nMove $order:")
                when (order) {
                    '<' -> {
                        var end = posX - 1
                        while (map[posY][end] == 'O') end--
                        if (map[posY][end] != '#') {
                            // not blocked
                            map[posY][end] = 'O'
                            map[posY][posX] = '.'
                            posX--
                            map[posY][posX] = '@'
                        }
                    }

                    '^' -> {
                        var end = posY - 1
                        while (map[end][posX] == 'O') end--
                        if (map[end][posX] != '#') {
                            // not blocked
                            map[end][posX] = 'O'
                            map[posY][posX] = '.'
                            posY--
                            map[posY][posX] = '@'
                        }
                    }

                    '>' -> {
                        var end = posX + 1
                        while (map[posY][end] == 'O') end++
                        if (map[posY][end] != '#') {
                            // not blocked
                            map[posY][end] = 'O'
                            map[posY][posX] = '.'
                            posX++
                            map[posY][posX] = '@'
                        }
                    }

                    'v' -> {
                        var end = posY + 1
                        while (map[end][posX] == 'O') end++
                        if (map[end][posX] != '#') {
                            // not blocked
                            map[end][posX] = 'O'
                            map[posY][posX] = '.'
                            posY++
                            map[posY][posX] = '@'
                        }
                    }

                    else -> {
                        throw IllegalStateException("unknown order $order")
                    }
                }
                //crates.forEach { it.asList().joinToString("").println() }
            }
        return crates.withIndex().sumOf { line->
            line.value.withIndex().sumOf { square ->
                if (square.value=='O')
                    square.index + 100*line.index
                else
                    0
            }
        }//.also { it.println() }
    }

    fun canLeft(x: Int, y: Int, crates: MutableList<CharArray>): Boolean {
        if (crates[y][x] == '.') return true
        if (crates[y][x] == '#') return false
        if (crates[y][x] == ']') return canLeft(x-2, y, crates)
        throw IllegalStateException("Not possible")
    }

    fun doLeft(x: Int, y: Int, crates: MutableList<CharArray>) {
        var xx = x
        while (crates[y][xx] != '.') {
            if (crates[y][xx] == '[')
                crates[y][xx] = ']'
            else
                crates[y][xx] = '['
            xx--
        }
        crates[y][xx] = '['
    }

    fun canUp(x: Int, y: Int, crates: MutableList<CharArray>): Boolean {
        if (crates[y][x] == '.') return true
        if (crates[y][x] == '#') return false
        if (crates[y][x] == '[') return canUp(x, y-1, crates) && canUp(x+1, y-1, crates)
        if (crates[y][x] == ']') return canUp(x-1, y-1, crates) && canUp(x, y-1, crates)
        throw IllegalStateException("Not possible")
    }

    fun doUp(x: Int, y: Int, crates: MutableList<CharArray>) {
        if (crates[y][x] == '[') {
            doUp(x, y-1, crates); doUp(x+1, y-1, crates)
            crates[y-1][x] = '['
            crates[y-1][x+1] = ']'
            crates[y][x+1] = '.'
        }
        if (crates[y][x] == ']') {
            doUp(x-1, y-1, crates); doUp(x, y-1, crates)
            crates[y-1][x-1] = '['
            crates[y-1][x] = ']'
            crates[y][x-1] = '.'
        }
    }

    fun canRight(x: Int, y: Int, crates: MutableList<CharArray>): Boolean {
        if (crates[y][x] == '.') return true
        if (crates[y][x] == '#') return false
        if (crates[y][x] == '[') return canRight(x+2, y, crates)
        throw IllegalStateException("Not possible")
    }

    fun doRight(x: Int, y: Int, crates: MutableList<CharArray>) {
        var xx = x
        while (crates[y][xx] != '.') {
            if (crates[y][xx] == '[')
                crates[y][xx] = ']'
            else
                crates[y][xx] = '['
            xx++
        }
        crates[y][xx] = ']'
    }

    fun canDown(x: Int, y: Int, crates: MutableList<CharArray>): Boolean {
        if (crates[y][x] == '.') return true
        if (crates[y][x] == '#') return false
        if (crates[y][x] == '[') return canDown(x, y+1, crates) && canDown(x+1, y+1, crates)
        if (crates[y][x] == ']') return canDown(x-1, y+1, crates) && canDown(x, y+1, crates)
        throw IllegalStateException("Not possible")
    }

    fun doDown(x: Int, y: Int, crates: MutableList<CharArray>) {
        if (crates[y][x] == '[') {
            doDown(x, y+1, crates); doDown(x+1, y+1, crates)
            crates[y+1][x] = '['
            crates[y+1][x+1] = ']'
            crates[y][x+1] = '.'
        }
        if (crates[y][x] == ']') {
            doDown(x-1, y+1, crates); doDown(x, y+1, crates)
            crates[y+1][x-1] = '['
            crates[y+1][x] = ']'
            crates[y][x-1] = '.'
        }
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.toCharArray() }.toTypedArray()
        var posX = 0
        var posY = 0
        var height = 0
        val crates = mutableListOf<CharArray>()
        for (y in map.indices) {
            if (map[y].isEmpty()) {
                height = y
                break
            }
            val line = mutableListOf<Char>()
            for (square in map[y]) {
                when(square) {
                    '#' -> {line += '#'; line += '#'}
                    '.' -> {line += '.'; line += '.'}
                    '@' -> {line += '@'; line += '.'}
                    'O' -> {line += '['; line += ']'}
                }
            }
            val xX = line.indexOf('@')
            if (xX != -1) {
                posX = xX
                posY = y
            }
            crates += line.toCharArray()
        }
        val orders = map.sliceArray(height+1..map.lastIndex)
        //crates.forEach { it.asList().joinToString("").println() }

        for(line in orders)
            for(order in line) {
                //println("\nMove $order:")
                when (order) {
                    '<' -> {
                        if (canLeft(posX - 1, posY, crates)) {
                            doLeft(posX - 1, posY, crates)
                            crates[posY][posX] = '.'
                            posX--
                            crates[posY][posX] = '@'
                        }
                    }

                    '^' -> {
                        if (canUp(posX, posY-1, crates)) {
                            doUp(posX, posY-1, crates)
                            crates[posY][posX] = '.'
                            posY--
                            crates[posY][posX] = '@'
                        }
                    }

                    '>' -> {
                        if (canRight(posX + 1, posY, crates)) {
                            doRight(posX + 1, posY, crates)
                            crates[posY][posX] = '.'
                            posX++
                            crates[posY][posX] = '@'
                        }
                    }

                    'v' -> {
                        if (canDown(posX, posY+1, crates)) {
                            doDown(posX, posY+1, crates)
                            crates[posY][posX] = '.'
                            posY++
                            crates[posY][posX] = '@'
                        }
                    }

                    else -> {
                        throw IllegalStateException("unknown order $order")
                    }
                }
                //crates.forEach { it.asList().joinToString("").println() }
            }
        return crates.withIndex().sumOf { line->
            line.value.withIndex().sumOf { square ->
                if (square.value=='[')
                    square.index + 100*line.index
                else
                    0
            }
        }//.also { it.println() }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 2028)
    val testInput2 = readInput("Day15_test2")
    check(part1(testInput2) == 10092)

    val testInput3 = readInput("Day15_test3")
    check(part2(testInput3) > 0)
    check(part2(testInput2) == 9021)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
