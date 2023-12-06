package day06

import readInput

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = readInput("06", "in")
    println(input.first().removePrefix("Time:").split(' ').filter { it.isNotEmpty() }.map { it.toInt() }.let { times ->
        input[1].removePrefix("Distance:").split(' ').filter { it.isNotEmpty() }.map { it.toInt() }.let { distances ->
            (0..3).fold(1) { acc, i -> acc * (1..<times[i]).count { hold -> distances[i] < (times[i] - hold) * hold } }
        }
    })
}

fun part2() {
    val input = readInput("06", "in")
    println(input.first().removePrefix("Time:").filterNot { it == ' ' }.toLong().let { time ->
        input[1].removePrefix("Distance:").filterNot { it == ' ' }.toLong().let { distance ->
            (1..<time).count { hold -> distance < (time - hold) * hold }
        }
    })
}
