fun main() {

    fun addNeighbour(neighbours: MutableMap<String, MutableSet<String>>, c1: String, c2: String) {
        neighbours.compute(c1) { _, list ->
            list?.also { it.add(c2) } ?: mutableSetOf(c2)
        }
    }

    fun part1(input: List<String>): Int {
        val connections = mutableSetOf<Pair<String, String>>()
        val neighbours = mutableMapOf<String, MutableSet<String>>()
        for (link in input) {
            val computers = link.split("-")
            addNeighbour(neighbours, computers[0], computers[1])
            addNeighbour(neighbours, computers[1], computers[0])
            connections.add(Pair(computers[0], computers[1]))
            connections.add(Pair(computers[1], computers[0]))
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
                for (c in list) {
                    for (c2 in list)
                        if (c2!=c && Pair(c,c2) in connections)
                            set.add(mutableSetOf(name, c, c2))
                }
                set
            }.flatten().toSet()
        return sets.size
    }

    fun addNetworks(
        neighbours: MutableMap<String, MutableSet<String>>,
        candidates: Array<String>,
        idx: Int,
        networks: MutableSet<Set<String>>,
        network: Set<String>
    ) {
        if (idx >= candidates.size) {
            networks += network
        } else {
            val candidate = candidates[idx]
            val neighbors = neighbours[candidate]!!
            if (neighbors.containsAll(network))
                addNetworks(neighbours, candidates, idx+1, networks, network + candidate)
            addNetworks(neighbours, candidates, idx+1, networks, network)
        }
    }

    fun part2(input: List<String>): String {
        val neighbours = mutableMapOf<String, MutableSet<String>>()
        for (link in input) {
            val computers = link.split("-")
            addNeighbour(neighbours, computers[0], computers[1])
            addNeighbour(neighbours, computers[1], computers[0])
        }
        val computers = neighbours.keys.sorted()
        val networks = mutableSetOf<Set<String>>()
        for (comp in computers) {
            addNetworks(neighbours,neighbours[comp]!!.sorted().toTypedArray(), 0, networks, mutableSetOf(comp))
        }
        return networks.maxBy { it.size }.joinToString(",")
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day23_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == "co,de,ka,ta")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day23")
    part1(input).println() // 1662 too high
    part2(input).println()
}
