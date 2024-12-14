data class Robot(var x:Int, var y: Int, val vx: Int, val vy: Int)
fun main() {
    fun part1(input: List<String>, sX : Int, sY: Int): Long {
        val robots = input.map { line ->
            val ints = Regex("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)").matchEntire(line)!!.groupValues
            Robot(ints[1].toInt(), ints[2].toInt(), ints[3].toInt(), ints[4].toInt())
        }
        var topLeft = 0L
        var topRight = 0L
        var bottomLeft = 0L
        var bottomRight = 0L
        for(robot in robots) {
            robot.x = (robot.x + 100*robot.vx) % sX
            robot.y = (robot.y + 100*robot.vy) % sY
            if (robot.x < 0) robot.x += sX
            if (robot.y < 0) robot.y += sY
            if (robot.x < sX/2) {
                if (robot.y < sY/2)
                    topLeft ++
                else if (robot.y > sY/2)
                    bottomLeft ++
            }
            else if (robot.x > sX/2) {
                if (robot.y < sY/2)
                    topRight ++
                if (robot.y > sY/2)
                    bottomRight ++
            }
        }

        return topLeft*topRight*bottomLeft*bottomRight
    }

    fun part2(input: List<String>, sX : Int, sY: Int): Int {
        val robots = input.map { line ->
            val ints = Regex("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)").matchEntire(line)!!.groupValues
            Robot(ints[1].toInt(), ints[2].toInt(), ints[3].toInt(), ints[4].toInt())
        }
        repeat(10000) { iter ->
            val counts = Array(sY){IntArray(sX)}
            for (robot in robots) {
                robot.x = (robot.x + robot.vx) % sX
                robot.y = (robot.y + robot.vy) % sY
                if (robot.x < 0) robot.x += sX
                if (robot.y < 0) robot.y += sY
                counts[robot.y][robot.x]++
            }
            if (iter>=0) {
                println("Iteration $iter")
                for (line in counts) {
                    for (nb in line) {
                        if (nb > 0) print('*') else print(' ')
                    }
                    println()
                }
            }
        }
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day14_test")
    check(part1(testInput,11,7) == 12L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day14")
    part1(input,101,103).println()
    part2(input,101,103).println()
}
