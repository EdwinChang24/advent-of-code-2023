package day02

import readInput

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = readInput("02", "in")
    println(input.sumOf { line ->
        line.split(':').let { game ->
            game[1].split(';').map { subset ->
                subset.split(',').associate { oneColor ->
                    oneColor.trim().split(' ').let { it[1] to it[0].toInt() }
                }
            }.takeIf { subsets ->
                subsets.all { cubeNums ->
                    (cubeNums["red"] ?: 0) <= 12 && (cubeNums["green"] ?: 0) <= 13 && (cubeNums["blue"] ?: 0) <= 14
                }
            }?.let { game[0].removePrefix("Game ").toInt() } ?: 0
        }
    })
}

fun part2() {
    val input = readInput("02", "in")
    println(input.sumOf { line ->
        line.split(':').let { game ->
            game[1].split(';').map { subset ->
                subset.split(',').associate { oneColor ->
                    oneColor.trim().split(' ').let { it[1] to it[0].toInt() }
                }
            }.reduce { accSubset, subset ->
                mapOf(
                    "red" to maxOf(accSubset["red"] ?: 0, subset["red"] ?: 0),
                    "green" to maxOf(accSubset["green"] ?: 0, subset["green"] ?: 0),
                    "blue" to maxOf(accSubset["blue"] ?: 0, subset["blue"] ?: 0)
                )
            }.let { minimums ->
                (minimums["red"] ?: 0) * (minimums["green"] ?: 0) * (minimums["blue"] ?: 0)
            }
        }
    })
}
