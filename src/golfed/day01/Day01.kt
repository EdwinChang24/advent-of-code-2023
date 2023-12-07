package golfed.day01

import java.io.File

fun main() {
    part1()
    part2()
}
val input = File("src/golfed/day01/in.txt")

fun part1()=println(input.readLines().sumOf{("${it.first{c->c.isDigit()}}"+it.last{c->c.isDigit()}).toInt()})

fun part2(){val m=mapOf("one" to 1,"two" to 2,"three" to 3,"four" to 4,"five" to 5,"six" to 6,"seven" to 7,"eight" to 8,"nine" to 9)+(1..9).map{"$it" to it};println(input.readLines().sumOf{"${m[it.findAnyOf(m.keys)?.second]}${m[it.findLastAnyOf(m.keys)?.second]}".toInt()})}
