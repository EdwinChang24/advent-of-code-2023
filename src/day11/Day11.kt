package day11

import readInput
import kotlin.math.abs

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = readInput("11", "in")
    println(input.indices.filterNot { '#' in input[it] }.let { expandedRows ->
        input.first().indices.filterNot { '#' in input.map { l -> l[it] } }.let { expandedCols ->
            input.flatMapIndexed { index, _ ->
                input[index].mapIndexedNotNull { index2, c -> if (c == '#') index to index2 else null }
            }.let { galaxies ->
                galaxies.sumOf { galaxy ->
                    galaxies.sumOf { galaxy2 ->
                        abs(galaxy.first - galaxy2.first) + expandedRows.count { it in (galaxy.first..galaxy2.first) + (galaxy2.first..galaxy.first) } + abs(
                            galaxy.second - galaxy2.second
                        ) + expandedCols.count { it in (galaxy.second..galaxy2.second) + (galaxy2.second..galaxy.second) }
                    }
                } / 2
            }
        }
    })
}

fun part2() {
    val input = readInput("11", "in")
    println(input.indices.filterNot { '#' in input[it] }.let { expandedRows ->
        input.first().indices.filterNot { '#' in input.map { l -> l[it] } }.let { expandedCols ->
            input.flatMapIndexed { index, _ ->
                input[index].mapIndexedNotNull { index2, c -> if (c == '#') index to index2 else null }
            }.let { galaxies ->
                galaxies.sumOf { galaxy ->
                    galaxies.sumOf { galaxy2 ->
                        abs(galaxy.first - galaxy2.first) + (999_999L * expandedRows.count { it in (galaxy.first..galaxy2.first) + (galaxy2.first..galaxy.first) }) + abs(
                            galaxy.second - galaxy2.second
                        ) + (999_999L * expandedCols.count { it in (galaxy.second..galaxy2.second) + (galaxy2.second..galaxy.second) })
                    }
                } / 2
            }
        }
    })
}
