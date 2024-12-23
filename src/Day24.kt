import java.io.PrintWriter

enum class Instr {
    AND {
        override fun compute(v1: Boolean, v2: Boolean): Boolean = v1 and v2
    },
    OR{
        override fun compute(v1: Boolean, v2: Boolean): Boolean = v1 or v2
    },
    XOR{
        override fun compute(v1: Boolean, v2: Boolean): Boolean = v1 xor v2
    };

    abstract fun compute(v1 : Boolean, v2 : Boolean) : Boolean
}
fun main() {

    val dependencies = mutableMapOf<String, Set<String>>()
    data class Instruction(val op1 : String, val ins : Instr, val op2 : String)

    fun compute(
        instructName: String,
        wires: MutableMap<String, Boolean>,
        instructions: MutableMap<String, Instruction>
    ) : Boolean {
        val instruct = instructions[instructName]!!
        val v1 = wires[instruct.op1]?:compute(instruct.op1,wires, instructions).also { wires[instruct.op1] = it }
        val v2 = wires[instruct.op2]?:compute(instruct.op2,wires, instructions).also { wires[instruct.op2] = it }
        dependencies[instructName] = dependencies[instruct.op1]!! + dependencies[instruct.op2]!!
        return instruct.ins.compute(v1, v2)
    }

    fun writeDot(wires: MutableMap<String, Boolean>, instructions: Map<String, Instruction>) {
        PrintWriter("Day24.dot").use { out->
            out.println("digraph v {")
            wires.forEach { (name, _) ->
                if (name[0] == 'x') {
                    out.println("$name [fillcolor=ALICEBLUE, style=filled]")
                } else if (name[0] == 'y') {
                    out.println("$name [fillcolor=YELLOW, style=filled]")
                }
            }
            instructions.forEach { name, (op1, op, op2) ->
                if (name[0]=='z') {
                    out.println("$name [label=\"$op\n$name\", fillcolor=OLIVE, style=filled]")
                } else {
                    out.println("$name [label=\"$op\n$name\"]")
                }
                out.println("$op1 -> $name")
                out.println("$op2 -> $name")
            }
            out.println("}")
        }

    }

    fun part1(input: List<String>): Long {
        val ite = input.listIterator()
        var line : String
        val wires = mutableMapOf<String, Boolean>()
        val instructions = mutableMapOf<String, Instruction>()
        while (ite.next().also { line = it }.isNotBlank()) {
            val parts = line.split(": ")
            wires[parts[0]] = (parts[1] == "1")
            dependencies[parts[0]] = setOf(parts[0])
        }
        while (ite.hasNext()) {
            line = ite.next()
            val parts = Regex("([a-z0-9]{3}) ([A-Z]+) ([a-z0-9]{3}) -> ([a-z0-9]{3})").matchEntire(line)!!.groupValues
            instructions[parts[4]] = Instruction(parts[1], when (parts[2]) {
                "AND" -> Instr.AND
                "OR" -> Instr.OR
                else -> Instr.XOR
            }, parts[3])
        }
        writeDot(wires, instructions)
        for (wire in instructions.keys) {
            if (wire !in wires)
                wires[wire] = compute(wire, wires, instructions)
        }
        val zs = wires.filter { (name, _) -> name[0] == 'z' }.toList().sortedByDescending { it.first }
            .onEach { variable -> println("$variable <- ${dependencies[variable.first].toString()}")}
            .map { if(it.second) '1' else '0'}.joinToString(separator = "")
        return java.lang.Long.parseLong(zs, 2)
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day24_test")
    check(part1(testInput) == 4L)
    val testInput2 = readInput("Day24_test2")
    check(part1(testInput2) == 2024L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day24")
    part1(input).println()
    val inputC = readInput("Day24Corrected")
    part1(inputC).println() //vcg z24 z20 jgb rrs rvc z09 rkf
    // jgb,rkf,rrs,rvc,vcg,z09,z20,z24
}
