import java.util.Comparator
import java.util.PriorityQueue

fun main() {
    data class State(val x:Int, val y:Int) {
        var distance : Int = 0
        var history : Set<State> = emptySet()

        constructor(x : Int,y: Int, dist: Int, hist : Set<State>) : this(x,y) {
            distance = dist
            history = hist
        }
        fun up() = State(x,y-1, distance+1, history+this)
        fun down() = State(x,y+1, distance+1, history+this)
        fun left() = State(x-1,y, distance+1, history+this)
        fun right() = State(x+1,y, distance+1, history+this)
    }

    fun computePath(sX: Int, sY: Int, map: Array<CharArray>): Set<State> {
        val queue = PriorityQueue<State>(Comparator.comparingInt { s -> s.distance })
        queue.add(State(0, 0, 0, emptySet()))
        val visited = mutableSetOf<State>()
        while (queue.isNotEmpty()) {
            val here = queue.poll()
            if (here.x == sX && here.y == sY)
                return here.history
            if (here in visited)
                continue
            visited += here
            var next = here.up()
            if (next.y in map.indices && next.x in 0..sX
                && map[next.y][next.x] != '#'
                && next !in visited
            )
                queue.add(next)
            next = here.down()
            if (next.y in map.indices && next.x in 0..sX
                && map[next.y][next.x] != '#'
                && next !in visited
            )
                queue.add(next)
            next = here.left()
            if (next.y in map.indices && next.x in 0..sX
                && map[next.y][next.x] != '#'
                && next !in visited
            )
                queue.add(next)
            next = here.right()
            if (next.y in map.indices && next.x in 0..sX
                && map[next.y][next.x] != '#'
                && next !in visited
            )
                queue.add(next)
        }
        throw IllegalStateException("no exit")
    }

    fun part1(input: List<String>, sX: Int, sY: Int, nbBytes: Int): Int {
        val map = Array(sY+1) { CharArray(sX+1) { '.' } }
        input.take(nbBytes).forEach { line ->
            val coords = line.split(",").map { it.toInt() }
            map[coords[1]][coords[0]] = '#'
        }
        return computePath(sX, sY, map).size
    }

    fun part2(input: List<String>, sX: Int, sY: Int, nbBytes: Int): String {
        val map = Array(sY+1) { CharArray(sX+1) { '.' } }
        var path = emptySet<State>()
        input.forEachIndexed { index,line ->
            val coords = line.split(",").map { it.toInt() }
            map[coords[1]][coords[0]] = '#'
            if (index>=nbBytes && (path.isEmpty() || State(coords[0], coords[1]) in path)) {
                try {
                    path = computePath(sX, sY, map)
                } catch (_ : IllegalStateException) {
                    return "${coords[0]},${coords[1]}"
                }
            }
        }
        return "none"
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day18_test")
    check(part1(testInput,6,6,12) == 22)
    check(part2(testInput,6,6,12) == "6,1")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day18")
    part1(input,70,70,1024).println()
    part2(input,70,70,1024).println()
}
