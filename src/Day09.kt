fun main() {
    fun part1(input: List<String>): Long {
        val original = input[0]
        val map = ArrayList<Int>()
        val ite = original.iterator()
        var fileNo = 0
        while (ite.hasNext()) {
            repeat(ite.next().code - '0'.code) {
                map.add(fileNo)
            }
            fileNo++
            if (ite.hasNext())
                repeat(ite.next().code - '0'.code) {
                    map.add(-1)
                }
        }
        while(map.last == -1) map.removeLast() // remove final gap

        var index = 0
        while (index<map.size) {
            if (map[index] == -1)
                map[index] = map.removeLast()
            index++
            while(map.last == -1) map.removeLast() // remove final gap
        }

        val hashCode = map.withIndex().sumOf {
            (it.value * it.index).toLong()
        }

        return hashCode
    }

    fun part2(input: List<String>): Long {
        val original = input[0]
        val map = ArrayList<Pair<Int,Int>>()
        val ite = original.iterator()
        var fileNo = 0
        while (ite.hasNext()) {
            map.add(Pair(fileNo, ite.next().code - '0'.code))
            fileNo++
            if (ite.hasNext())
                map.add(Pair(-1, ite.next().code - '0'.code))
        }

        var source = map.lastIndex
        var lastMoved = -1
        while (source>0){
            val current = map[source]
            if (current.first == -1) {
                source--
                continue
            }

            lastMoved = if (lastMoved == -1) current.first
            else if (lastMoved < current.first) { // already moved block
                source--
                continue
            } else
                current.first

            val dest = map.withIndex().find {
                it.value.first == -1 && it.value.second >= current.second
            }
            if (dest == null) source-- // not enough space
            else if(dest.index >= source) source-- // hole is after current
            else {
                val left = dest.value.second - current.second
                map[dest.index] = current // copy file
                map[source] = Pair(-1, current.second)  // remove original file
                if (left > 0)
                    map.add(dest.index+1, Pair(-1, left))
                else source--
            }
        }
        var hashCode = 0L
        var index = 0
        for (block in map) {
            if (block.first == -1)
                index += block.second
            else {
                repeat(block.second) {
                    hashCode += block.first * (index++)
                }
            }
        }
        return hashCode
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("2333133121414131402")) == 1928L)
    check(part2(listOf("2333133121414131402")) == 2858L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
