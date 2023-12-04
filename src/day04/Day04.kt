package day04

import readInput
import java.math.BigInteger

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = readInput("04", "in")
    println(input.sumOf { line ->
        line.substring(10..<39).split(' ').filter { it.isNotEmpty() }.let { winning ->
            line.substring(42..<116).split(' ').filter { it.isNotEmpty() }.let { given ->
                (given.count { it in winning } - 1).let { pow ->
                    pow.takeIf { it >= 0 }?.let { BigInteger.valueOf(2).pow(it).toInt() } ?: 0
                }
            }
        }
    })
}

fun part2() {
    val input = readInput("04", "in")
    println(input.map { line ->
        line.substring(10..<39).split(' ').filter { it.isNotEmpty() }.let { winning ->
            line.substring(42..<116).split(' ').filter { it.isNotEmpty() }.let { given ->
                given.count { it in winning }
            }
        }
    }.let { cards ->
        cards.foldIndexed(List(cards.size) { 1 }) { index, acc, winCount ->
            acc.mapIndexed { index1, cardCount ->
                if (index1 in (index + 1)..(index + winCount)) cardCount + acc[index] else cardCount
            }
        }.sum()
    })
}
