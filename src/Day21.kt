fun main() {
    val targetsNum= mapOf(
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
    val targetsPad= mapOf(
        'A' to Pair(2,0),
        '^' to Pair(1,0),
        ' ' to Pair(0,0),
        '<' to Pair(0,1),
        'v' to Pair(1,1),
        '>' to Pair(2,1),
    )
    fun typeCode(code: String, effector: (String) -> String) : String {
        var x = 2
        var y = 3
        var total = ""
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
                    total += effector(manip.toString())
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
                    val effector1 = effector(manip.toString())
                    val effector2 = effector(manip2.toString())
                    total += if (effector1.length < effector2.length) effector1 else effector2
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
                    total += effector(manip.toString())
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
                    val effector1 = effector(manip.toString())
                    val effector2 = effector(manip2.toString())
                    total += if (effector1.length < effector2.length) effector1 else effector2
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
                    total += effector(manipX.toString()+manipY.toString()+'A')
                else {
                    val lenXY = effector(manipX.toString() + manipY.toString() + 'A')
                    val lenYX = effector(manipY.toString() + manipX.toString() + 'A')
                    total += if (lenXY.length < lenYX.length) lenXY else lenYX
                }
            }
        }
        return total
    }
    fun typePad(code: String, effector: (String)->String) : String {
        var x=2
        var y=0
        var total = ""
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
                total += effector(manip.toString())
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
                total += effector(manip.toString())
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
                val lenXY = effector(manipX.toString()+manipY.toString()+'A')
                val lenYX = effector(manipY.toString()+manipX.toString()+'A')
                total += if (lenXY.length < lenYX.length) lenXY else lenYX
            }
        }
        return total
    }
    fun part1(input: List<String>): Long {
        var total = 0L
        for (code in input) {
            val len = typeCode(code) {
                seq -> typePad(seq) {
                    seq2 -> typePad(seq2) {
                        seqF -> seqF
                    }
                }
            }.also { it.println() }.length
            val value = code.dropLast(1).toInt()
            println("code -> $len * $value")
            total += value * len
        }
        return total
    }

    fun part2(input: List<String>): Long {
        var total = 0L
        for (code in input) {
            val len = typeCode(code) {
                    seq1 -> typePad(seq1) {
                    seq2 -> typePad(seq2) {
                    seq3 -> typePad(seq3) {
                    seq4 -> typePad(seq4) {
                    //seq5 -> typePad(seq5) {
                     seqF -> seqF
                    //}
                    }}}}
            }.also { it.println() }.length
            val value = code.dropLast(1).toInt()
            println("code -> $len * $value")
            total += value * len
        }
        return total
    }

    //check(part2(listOf("2"))>0L)// v<A<A>>^Av<<A>>^AvAA<^A>Av<A<AA>>^AvAA<^A>AAv<A>^AAv<<A>^A>AvA^Av<A<A>>^AvA<^A>Av<A<AA>>^AvA<^A>AvA^Av<A>^A<A>Av<A<A>>^AvA<^A>Av<<A>>^AvA^A


    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day21_test")
    check(part1(testInput) == 126384L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day21")
    println("Part1")
    part1(input).println() //197398 too low 210230 too high
    part2(input).println()
}
