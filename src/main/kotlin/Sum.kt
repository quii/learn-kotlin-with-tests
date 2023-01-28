fun sum(numbers: List<Int>): Int {
    var sum = 0
    for (number in numbers) {
        sum += number
    }
    return sum
}

fun sum2(numbers: List<Int>): Int = numbers.fold(0) { acc, i -> acc + i }

fun sum3(vararg numbers: Int): Int = numbers.fold(0) { acc, i -> acc + i }