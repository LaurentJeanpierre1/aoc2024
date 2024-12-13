import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val ite = input.iterator()
        var totalTokens = 0
        while (ite.hasNext()) {
            val buttonA = Regex("Button A: X\\+(\\d+), Y\\+(\\d+)").matchEntire(ite.next())
            val buttonB = Regex("Button B: X\\+(\\d+), Y\\+(\\d+)").matchEntire(ite.next())
            val prize = Regex("Prize: X=(\\d+), Y=(\\d+)").matchEntire(ite.next())
            check( (!ite.hasNext()) || ite.next().isBlank())
            val aX = buttonA!!.groupValues[1].toInt()
            val aY = buttonA.groupValues[2].toInt()
            val bX = buttonB!!.groupValues[1].toInt()
            val bY = buttonB.groupValues[2].toInt()
            val pX = prize!!.groupValues[1].toInt()
            val pY = prize.groupValues[2].toInt()
            var countA = min(100, min(pX/aX, pY/aY))
            var countB = (pX - countA*aX) / bX
            var minTokens = 1000
            while (countA>0 && countB<100) {
                if (pX == aX*countA + bX*countB && pY == aY*countA + bY*countB) {
                    val tokens = 3*countA + countB
                    if (minTokens > tokens)
                        minTokens = tokens
                }
                countA--
                countB = (pX - countA*aX) / bX
            }
            if (minTokens < 1000) {
                totalTokens += minTokens
                println("Machine -> $minTokens")
            } else
                println("Machine sans prix")
        }
        return totalTokens
    }

    fun part2(input: List<String>): Long {
        val ite = input.iterator()
        var totalTokens = 0L
        while (ite.hasNext()) {
            val buttonA = Regex("Button A: X\\+(\\d+), Y\\+(\\d+)").matchEntire(ite.next())
            val buttonB = Regex("Button B: X\\+(\\d+), Y\\+(\\d+)").matchEntire(ite.next())
            val prize = Regex("Prize: X=(\\d+), Y=(\\d+)").matchEntire(ite.next())
            check( (!ite.hasNext()) || ite.next().isBlank())
            val aX = buttonA!!.groupValues[1].toInt()
            val aY = buttonA.groupValues[2].toInt()
            val bX = buttonB!!.groupValues[1].toInt()
            val bY = buttonB.groupValues[2].toInt()
            val pX = 10000000000000L + prize!!.groupValues[1].toInt()
            val pY = 10000000000000L + prize.groupValues[2].toInt()
            var countB = min(pX/bX, pY/bY)
            var countA = (pX - countB*bX) / aX
            var minTokens = 30000000000000L
            while (countB>0) {
                if (pX == aX*countA + bX*countB && pY == aY*countA + bY*countB) {
                    val tokens = 3*countA + countB
                    if (minTokens > tokens)
                        minTokens = tokens
                }
                countB--
                countA = (pX - countB*bX) / aX
            }
            if (minTokens < 30000000000000L) {
                totalTokens += minTokens
                println("Machine -> $minTokens")
            } else
                println("Machine sans prix")
        }
        return totalTokens
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 480)
    check(part2(testInput) > 480)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
