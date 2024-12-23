fun main() {

    fun addNeighbour(neighbours: MutableMap<String, MutableSet<String>>, c1: String, c2: String) {
        neighbours.compute(c1) { _, list ->
            list?.also { it.add(c2) } ?: mutableSetOf(c2)
        }
    }

    fun part1(input: List<String>): Int {
        val neighbours = mutableMapOf<String, MutableSet<String>>()
        for (link in input) {
            val computers = link.split("-")
            addNeighbour(neighbours, computers[0], computers[1])
            addNeighbour(neighbours, computers[1], computers[0])
        }
/*
        for ((c,l) in neighbours){
            for (c2 in l)
                neighbours[c2]!!.addAll(l)
        }
        val networks = neighbours.values.toSet()
*/
        val sets = neighbours.filter { it.key[0] == 't' }
            .also { it.println() }
            .map { (name, list) ->
                val set = mutableSetOf<MutableSet<String>>()
                for (c in list)
                    set.add(mutableSetOf(name, c))
                set.flatMap { s->list.map { s+it } }.filter { it.size==3 }.toSet()
            }.flatten().toSet()
        return sets.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day23_test")
    //check(part1(testInput) == 7)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day23")
    part1(input).println() // 1662 too high
    part2(input).println()
}
