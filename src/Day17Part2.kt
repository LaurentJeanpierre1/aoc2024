fun main() {
    fun back(prefix: Long, expected : List<Int>, index: Int) : Long {
        if (index < 0) return prefix
        for (digit in 0..7) {
            val a = prefix*8 + digit
            val b1 = digit xor 0b011
            val c = a shr b1
            val b2 = b1.toLong() xor c
            val b3 = b2 xor 0b101
            if (expected[index] == (b3 and 7).toInt()) try {
                return back(a, expected, index - 1)
            } catch (_ : IllegalStateException) {

            }
        }
        throw IllegalStateException("No solution with this prefix")
    }

    back(0L, listOf(2,4,1,3,7,5,0,3,4,3,1,5,5,5,3,0), 15).println()
}
