package day01

import readInput

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = readInput("01", "in")
    println(input.sumOf { line -> (line.first { it.isDigit() }.toString() + line.last { it.isDigit() }).toInt() })
}

fun part2() {
    val input = readInput("01", "in")
    val map = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    ) + (1..9).map { it.toString() to it }
    println(input.sumOf { line ->
        (line.findAnyOf(map.keys)!!.second.let { map[it]!! }
            .toString() + line.findLastAnyOf(map.keys)!!.second.let { map[it]!! }.toString()).toInt()
    })
}
