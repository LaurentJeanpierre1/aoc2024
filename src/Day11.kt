fun main() {
    fun blink(stones: MutableList<Long>) {
        val ite = stones.listIterator()
        while (ite.hasNext()) {
            val stone = ite.next()
            if (stone == 0L) ite.set(1L)
            else {
                val str = stone.toString()
                if (str.length % 2 == 0) {
                    ite.set(str.substring(0,str.length/2).toLong())
                    ite.add(str.substring(str.length/2).toLong())
                } else ite.set(stone * 2024)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val stones = input[0].split(" ").map { it.toLong() }.toMutableList()
        repeat(25) {
            blink(stones)
            //println(stones)
        }
        return stones.size
    }

    val cache: MutableMap<Pair<Long,Int>, Long> = mutableMapOf()

    fun blinker(nb: Int, stones: List<Long>, prefix: String): Long {
        println("$prefix ${stones.size} stones ${cache.size} cache")
        if (nb == 0) return stones.size.toLong()
        else {
            var total = 0L
            var left = stones.size
            for (stone in stones) {
                --left
                val state = Pair(stone, nb)
                if (state in cache)
                    total += cache[state]!!
                else {
                    val temp = mutableListOf(stone)
                    blink(temp)
                    val res = blinker(nb-1, temp, "$prefix $left")
                    cache[state] = res
                    total += res
                }
            }
            return total
        }
    }

    fun part2(input: List<String>, nb: Int): Long {
        val stones = input[0].split(" ").map { it.toLong() }.toMutableList()
        val res = blinker(nb, stones, "")
        return res
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("125 17")) == 55312)
    check(part2(listOf("125 17"), 25) == 55312L)
    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day11")
    part1(input).println()
    part2(input, 75).println()
}
