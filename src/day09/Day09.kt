package day09

import readInput

fun main() {
    part1()
    part2()
}

private fun sequences(sequence: List<Int>): List<List<Int>> = if (sequence.all { it == 0 }) listOf(sequence) else {
    listOf(sequence) + sequences(sequence.mapIndexed { index, i -> i - sequence.getOrElse(index - 1) { 0 } }
        .filterIndexed { index, _ -> index != 0 })
}

fun part1() {
    val input = readInput("09", "in")
    println(input.sumOf { line ->
        line.split(' ').map { it.toInt() }.let { nums -> sequences(nums).sumOf { it.last() } }
    })
}

fun part2() {
    val input = readInput("09", "in")
    println(input.sumOf { line ->
        line.split(' ').map { it.toInt() }.let { nums ->
            sequences(nums).foldIndexed(0) { index, acc, ints -> if (index % 2 == 0) acc + ints.first() else acc - ints.first() }
        }.toInt()
    })
}
