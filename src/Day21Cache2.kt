class Day21Cache2 {
    private val targetsNum= mapOf(
        'A' to Pair(2,3),
        '0' to Pair(1,3),
        ' ' to Pair(0,3),
        '1' to Pair(0,2),
        '2' to Pair(1,2),
        '3' to Pair(2,2),
        '4' to Pair(0,1),
        '5' to Pair(1,1),
        '6' to Pair(2,1),
        '7' to Pair(0,0),
        '8' to Pair(1,0),
        '9' to Pair(2,0),
    )
    private val targetsPad= mapOf(
        'A' to Pair(2,0),
        '^' to Pair(1,0),
        ' ' to Pair(0,0),
        '<' to Pair(0,1),
        'v' to Pair(1,1),
        '>' to Pair(2,1),
    )
    private data class Context(val prev: Char, val current: Char)
    private val cache = Array(26) { mutableMapOf<String, Long>() }

    fun typeCode(code: String, depth: Int) : Long {
        var x = 2
        var y = 3
        var total = 0L
        for (letter in code) {
            val target = targetsNum[letter]!!
            if (y == 3) {
                val manip = StringBuilder()
                var px=x
                var py=y
                while (target.second < y) {
                    manip.append('^')
                    y--
                    check(x!=0 || y !=3)
                }
                while (target.first < x) {
                    manip.append('<')
                    x--
                    check(x!=0 || y !=3)
                }
                while (target.first > x) {
                    manip.append('>')
                    x++
                    check(x!=0 || y !=3)
                }
                manip.append('A')
                if (target.first == 0 || target.first==px || target.second==py) {
                    total += compute(manip.toString(), depth)
                } else {
                    val manip2 = StringBuilder()
                    while (target.first < px) {
                        manip2.append('<')
                        px--
                        check(px!=0 || py !=3)
                    }
                    while (target.first > px) {
                        manip2.append('>')
                        px++
                        check(px!=0 || py !=3)
                    }
                    while (target.second < py) {
                        manip2.append('^')
                        py--
                        check(px!=0 || py !=3)
                    }
                    manip2.append('A')
                    val effector1 = compute(manip.toString(), depth)
                    val effector2 = compute(manip2.toString(), depth)
                    total += if (effector1 < effector2) effector1 else effector2
                }
            } else if (target.second == 3) {
                val manip = StringBuilder()
                var px=x
                var py=y
                while (target.first < x) {
                    manip.append('<')
                    x--
                    check(x!=0 || y !=3)
                }
                while (target.first > x) {
                    manip.append('>')
                    x++
                    check(x!=0 || y !=3)
                }
                while (target.second > y) {
                    manip.append('v')
                    y++
                    check(x!=0 || y !=3)
                }
                manip.append('A')
                if (px == 0 || target.first==px || target.second==py) {
                    total += compute(manip.toString(), depth)
                } else {
                    val manip2 = StringBuilder()
                    while (target.second > py) {
                        manip2.append('v')
                        py++
                        check(px!=0 || py !=3)
                    }
                    while (target.first < px) {
                        manip2.append('<')
                        px--
                        check(px!=0 || py !=3)
                    }
                    while (target.first > px) {
                        manip2.append('>')
                        px++
                        check(px!=0 || py !=3)
                    }
                    manip2.append('A')
                    val effector1 = compute(manip.toString(), depth)
                    val effector2 = compute(manip2.toString(), depth)
                    total += if (effector1 < effector2) effector1 else effector2
                }
            } else {
                val manipX = StringBuilder()
                val manipY = StringBuilder()
                while (target.first < x) {
                    manipX.append('<')
                    x--
                    check(x!=0 || y !=3)
                }
                while (target.first > x) {
                    manipX.append('>')
                    x++
                    check(x!=0 || y !=3)
                }
                while (target.second > y) {
                    manipY.append('v')
                    y++
                    check(x!=0 || y !=3)
                }
                while (target.second < y) {
                    manipY.append('^')
                    y--
                    check(x!=0 || y !=3)
                }
                if (manipX.isEmpty() || manipY.isEmpty())
                    total += compute(manipX.toString()+manipY.toString()+'A', depth)
                else {
                    val lenXY = compute(manipX.toString() + manipY.toString() + 'A', depth)
                    val lenYX = compute(manipY.toString() + manipX.toString() + 'A', depth)
                    total += if (lenXY < lenYX) lenXY else lenYX
                }
            }
        }
        return total
    }
    private fun typePad(code: String, depth: Int, fromKey: Char) : Long {
        var (x,y)=targetsPad[fromKey]!!

        var total = 0L
        for (letter in code) {
            val target = targetsPad[letter]!!
            if (y == 0) {
                val manip = StringBuilder()
                while (target.second > y) {
                    manip.append('v')
                    y++
                    check(x!=0 || y !=0)
                }
                while (target.first < x) {
                    manip.append('<')
                    x--
                    check(x!=0 || y !=0)
                }
                while (target.first > x) {
                    manip.append('>')
                    x++
                    check(x!=0 || y !=0)
                }
                manip.append('A')
                total += compute(manip.toString(), depth)
            } else if (target.second == 0) {
                val manip = StringBuilder()
                while (target.first < x) {
                    manip.append('<')
                    x--
                    check(x!=0 || y !=0)
                }
                while (target.first > x) {
                    manip.append('>')
                    x++
                    check(x!=0 || y !=0)
                }
                while (target.second < y) {
                    manip.append('^')
                    y--
                    check(x!=0 || y !=0)
                }
                manip.append('A')
                total += compute(manip.toString(), depth)
            } else {
                val manipX = StringBuilder()
                val manipY = StringBuilder()
                while (target.first < x) {
                    manipX.append('<')
                    x--
                    check(x!=0 || y !=0)
                }
                while (target.first > x) {
                    manipX.append('>')
                    x++
                    check(x!=0 || y !=0)
                }
                while (target.second > y) {
                    manipY.append('v')
                    y++
                    check(x!=0 || y !=0)
                }
                while (target.second < y) {
                    manipY.append('^')
                    y--
                    check(x!=0 || y !=0)
                }
                val lenXY = compute(manipX.toString()+manipY.toString()+ 'A', depth)
                val lenYX = compute(manipY.toString()+manipX.toString()+ 'A', depth)
                total += if (lenXY < lenYX) lenXY else lenYX
            }
        }
        return total
    }
    private fun compute(seq: String, depth: Int) : Long = if (depth==0) seq.length.toLong() else {
        cache[depth].computeIfAbsent(seq) {
            _ -> typePad(seq, depth-1, 'A')
        }
    }

}
fun main() {
    fun part1(input: List<String>): Long {
        var total = 0L
        val puzzle = Day21Cache2()
        for (code in input) {
            val len = puzzle.typeCode(code, 2)//.also { it.println() }
            val value = code.dropLast(1).toInt()
            println("code -> $len * $value")
            total += value * len
        }
        return total
    }

    fun part2(input: List<String>, depth: Int): Long {
        var total = 0L
        val puzzle = Day21Cache2()
        for (code in input) {
            val len = puzzle.typeCode(code, depth)//.also { it.println() }
            val value = code.dropLast(1).toInt()
            println("code -> $len * $value")
            total += value * len
        }
        return total
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day21_test")
    check(part1(testInput) == 126384L)
    //check(part2(listOf("2"),4) == 0L) // 139 chars

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day21")
    println("Part1")
    part1(input).println() //197398 too low 210230 too high

    println("Part2(2)")
    check(part2(input, 2)==203814L)
    println("Part2(5)")
    check(part2(input,5)==3252306L)
    println("Part2(15)")
    check(part2(input,15)==35952774200L)
    println("Part2(true one with cache)")
    part2(input, 25).println() //157 436 062 802 508 too low (24 robots) < 399 669 673 013 954 too high
}
