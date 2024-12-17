fun main() {
    fun Any.doNothing() { }
    class Processor{
        var pc = 0
        var a = 0L
        var b = 0L
        var c = 0L
        val memory = mutableListOf<Int>()
        val outValues = mutableListOf<Int>()

        fun readInput(input: List<String>) {
            a = Regex("Register A: (\\d+)").matchEntire(input[0])!!.groupValues[1].toLong()
            b = Regex("Register B: (\\d+)").matchEntire(input[1])!!.groupValues[1].toLong()
            c = Regex("Register C: (\\d+)").matchEntire(input[2])!!.groupValues[1].toLong()
            memory.clear()
            memory.addAll(
                Regex("Program: (\\d+(?:,\\d+)*)")
                    .matchEntire(input[4])!!
                    .groupValues[1]
                    .split(",")
                    .map { it.toInt() }
            )
        }

        fun process() {
            val op = memory[pc++]
            val param = memory[pc++].toLong()
            when(op) {
                0 -> adv(param)
                1 -> bxl(param)
                2 -> bst(param)
                3 -> jnz(param)
                4 -> bxc(param)
                5 -> out(param)
                6 -> bdv(param)
                7 -> cdv(param)
            }
        }

        private fun combo(param: Long) : Long = when(param) {
            0L -> 0L
            1L -> 1L
            2L -> 2L
            3L -> 3L
            4L -> a
            5L -> b
            6L -> c
            7L -> throw IllegalStateException("reserved")
            else -> throw IllegalStateException("illegal value")
        }
        private fun cdv(param: Long) {
            val denom = combo(param)
            c = if (denom>31) 0 else a shr denom.toInt()
        }

        private fun bdv(param: Long) {
            val denom = combo(param)
            b = if (denom>31) 0 else a shr denom.toInt()
        }

        private fun out(param: Long) {
            outValues += (combo(param) and 7).toInt()
        }

        private fun bxc(param: Long) {
            param.doNothing()
            b = b xor c
        }

        private fun jnz(param: Long) {
            if (a != 0L)
                pc = param.toInt()
        }

        private fun bst(param: Long) {
            b = combo(param) and 7
        }

        private fun bxl(param: Long) {
            b = b xor param
        }

        private fun adv(param: Long) {
            val denom = combo(param)
            a = if (denom>31) 0 else a shr denom.toInt()
        }
    }
    var proc: Processor
    fun part1(input: List<String>): String {
        proc = Processor()
        proc.readInput(input)
        try {
            while (true)
                proc.process()
        } catch (except : Exception) {
            except.printStackTrace()
        }
        return proc.outValues.joinToString(",")
    }

    fun part2(input: List<String>): Long {
        proc = Processor()
        proc.readInput(input)
        var firstA = -1L
        do {
            proc.a = ++firstA
            proc.pc = 0
            proc.outValues.clear()
            try {
                while (true) {
                    proc.process()
                    for (pair in proc.outValues.zip(proc.memory))
                        if (pair.first != pair.second) throw IllegalStateException("wrong output")
                }
            } catch (_ : Exception) { }
        } while (proc.outValues != proc.memory)
        return firstA
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    /*
    part1(readInput("Day17_test1"))
    check(proc.b == 1)
    check(part1(readInput("Day17_test2")) == "0,1,2")
    check(part1(readInput("Day17_test3")) == "4,2,5,6,7,7,7,7,3,1,0")
    check(proc.a == 0)
    part1(readInput("Day17_test4"))
    check(proc.b == 26)
    part1(readInput("Day17_test5"))
    check(proc.b == 44354)
    val testInput = readInput("Day17_test")
    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")
*/
    //val testInput6 = readInput("Day17_test6")
    //check(part2(testInput6) == 117440L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day17")
    println("Part1")
    part1(input).println()

    // not 6,2,0,3,0,6,0,0,3
    println("Part2")
    part1(readInput("Day17Part2")).println()
}
