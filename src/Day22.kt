fun main() {

    fun pseudorandom(seed: Long) : Long {
        var secret = (seed xor (seed shl 6)) and 16777215L
        secret = (secret xor (secret shr 5)) and 16777215L
        secret = (secret xor (secret shl 11)) and 16777215L
        return secret
    }
    fun part1(input: List<String>): Long {
        var sum = 0L
        for (secret in input.map { it.toLong() }) {
            var price = secret
            repeat(2000) { price = pseudorandom(price) }
            sum += price
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val allVariations = mutableMapOf<List<Int>, MutableList<Byte>>()
        for (secret in input.map { it.toLong() }) {
            var price = secret
            val prices = mutableListOf((secret % 10).toByte())
            repeat(2000) {
                price = pseudorandom(price)
                prices += (price % 10).toByte()
            }
            val variations = prices.zipWithNext().map { (p1,p2) -> p2-p1 }.windowed(4).zip(prices.drop(4))
            val firstVariations = mutableMapOf<List<Int>, Byte>()
            variations.forEach {
                firstVariations.computeIfAbsent(it.first) {
                    _ -> it.second
                }
            }
            firstVariations.forEach { (key:List<Int>, value: Byte)->
                allVariations.compute(key) { _: List<Int>, prices: MutableList<Byte>? ->
                    if (prices == null)
                        mutableListOf(value)
                    else
                        prices.also { it.add(value) }
                }

            }
        }
        return allVariations.values.maxOf { prices:List<Byte> -> prices.sum().toLong() }
    }

    var seed = 123L
    seed = pseudorandom(seed)
    check(seed == 15887950L)
    seed = pseudorandom(seed)
    check(seed == 16495136L)
    seed = pseudorandom(seed)
    check(seed == 527345L)
    seed = pseudorandom(seed)
    check(seed == 704524L)
    seed = pseudorandom(seed)
    check(seed == 1553684L)
    seed = pseudorandom(seed)
    check(seed == 12683156L)
    seed = pseudorandom(seed)
    check(seed == 11100544L)
    seed = pseudorandom(seed)
    check(seed == 12249484L)
    seed = pseudorandom(seed)
    check(seed == 7753432L)
    seed = pseudorandom(seed)
    check(seed == 5908254L)

    check(part2(listOf("123")) == 9L)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day22_test")
    check(part1(testInput) == 37327623L)
    val testInput2 = readInput("Day22_test2")
    check(part2(testInput2) == 23L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day22")
    part1(input).println()
    part2(input).println()
}
