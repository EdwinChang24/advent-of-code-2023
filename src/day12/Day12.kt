package day12

import readInput

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = readInput("12", "in")
    println(input.sumOf { line ->
        line.split(' ').let { (springs, nums) ->
            fun String.check() = runningFold(0) { acc, c -> if (c == '.') 0 else acc + 1 }.let {
                it.filterIndexed { index, i -> it.getOrElse(index + 1) { 0 } < i && index != 0 }
            }.takeIf { it.size == nums.split(',').size }?.mapIndexed { index, i -> i == nums.split(',')[index].toInt() }
                ?.let { it.all { b -> b } } ?: false

            fun rec(s: String): Int = if ('?' !in s) if (s.check()) 1 else 0
            else rec(s.replaceFirst('?', '.')) + rec(s.replaceFirst('?', '#'))
            rec(springs)
        }
    })
}

fun part2() {
    val input = readInput("12", "in")
    // TODO
}
