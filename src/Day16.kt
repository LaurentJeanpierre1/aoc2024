import java.util.Comparator
import java.util.Objects
import java.util.PriorityQueue
fun main() {
    data class State(val x: Int, val y: Int, val dir: Dir, val score: Int, val history: List<Coord> ) {
        fun forward() = when(dir) {
            Dir.UP -> State(x, y-1, dir, score+1, history+Coord(x,y))
            Dir.RIGHT -> State(x+1, y, dir, score+1, history+Coord(x,y))
            Dir.DOWN -> State(x, y+1, dir, score+1, history+Coord(x,y))
            Dir.LEFT -> State(x-1, y, dir, score+1, history+Coord(x,y))
        }
        fun left() = when (dir) {
            Dir.UP -> State(x, y, Dir.LEFT, score+1000, history+Coord(x,y))
            Dir.RIGHT -> State(x, y, Dir.UP, score+1000, history+Coord(x,y))
            Dir.DOWN -> State(x, y, Dir.RIGHT, score+1000, history+Coord(x,y))
            Dir.LEFT -> State(x, y, Dir.DOWN, score+1000, history+Coord(x,y))
        }
        fun right() = when (dir) {
            Dir.UP -> State(x, y, Dir.RIGHT, score+1000, history+Coord(x,y))
            Dir.RIGHT -> State(x, y, Dir.DOWN, score+1000, history+Coord(x,y))
            Dir.DOWN -> State(x, y, Dir.LEFT, score+1000, history+Coord(x,y))
            Dir.LEFT -> State(x, y, Dir.UP, score+1000, history+Coord(x,y))
        }
        override fun hashCode()=Objects.hash(x,y,dir)
        override fun equals(other: Any?) = (other is State) && (other.x==x) && (other.y==y) && (other.dir==dir)
    }
    fun part1(input: List<String>): Int {
        val map = input.map { it.toCharArray() }.toTypedArray()
        var posX = 0
        var posY = 0

        val dir = Dir.RIGHT

        for (y in map.indices)
            for (x in map[y].indices)
                if (map[y][x] == 'S') {
                    posX = x
                    posY = y
                }

        val queue = PriorityQueue(Comparator.comparingInt<State> { s -> s.score })
        queue.add(State(posX, posY, dir, 0, emptyList()))
        val visited = mutableSetOf(queue.peek())

        while (queue.isNotEmpty()) {
            val here = queue.poll()
            visited += here
            if (map[here.y][here.x] == 'E')
                return here.score
            var next = here.forward()
            if (map[next.y][next.x] != '#')
                if (! visited.contains(next))
                    queue += next
            next = here.left()
            if (! visited.contains(next))
                queue += next
            next = here.right()
            if (! visited.contains(next))
                queue += next
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.toCharArray() }.toTypedArray()
        var posX = 0
        var posY = 0
        val dir = Dir.RIGHT

        for (y in map.indices)
            for (x in map[y].indices)
                if (map[y][x] == 'S') {
                    posX = x
                    posY = y
                }

        val queue = PriorityQueue(Comparator.comparingInt<State> { s -> s.score })
        queue.add(State(posX, posY, dir, 0, emptyList()))
        val visited = mutableSetOf(queue.peek())
        var best = -1
        val places = mutableSetOf<Coord>()
        while (queue.isNotEmpty()) {
            val here = queue.poll()
            if (best != -1 && best<here.score)
                return places.size+1 // add arrival position
            if (map[here.y][here.x] == 'E') {
                best = here.score
                places.addAll(here.history)
            }
            visited += here
            var next = here.forward()
            if (map[next.y][next.x] != '#')
                if (! visited.contains(next))
                    queue += next
            next = here.left()
            if (! visited.contains(next))
                queue += next
            next = here.right()
            if (! visited.contains(next))
                queue += next
        }
        return -1
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 7036)
    check(part2(testInput) == 45)
    val testInput2 = readInput("Day16_test2")
    check(part1(testInput2) == 11048)
    check(part2(testInput2) == 64)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
