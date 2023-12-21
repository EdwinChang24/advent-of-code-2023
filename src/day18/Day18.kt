package day18

import readInput
import kotlin.math.abs

fun main() {
    part1()
    part2()
}

private data class Step(val dir: Pair<Int, Int>, val count: Int)

fun part1() {
    val input = readInput("18", "in")
    println(input.map { line ->
        line.split(' ').let { split ->
            Step(
                when (split[0]) {
                    "U" -> -1 to 0
                    "D" -> 1 to 0
                    "L" -> 0 to -1
                    "R" -> 0 to 1
                    else -> error(":(")
                }, split[1].toInt()
            )
        }
    }.let { steps ->
        steps.sumOf { it.count }.let { size ->
            List(size * 2 + 10) { List(size * 2 + 10) { '.' } }.let { initialGrid ->
                steps.fold(initialGrid to (size + 5 to size + 5)) { (grid, coords), (dir, count) ->
                    List(count + 1) { (coords.first + (dir.first * it)) to (coords.second + (dir.second * it)) }.let { covered ->
                        grid.mapIndexed { rowIndex, row ->
                            if (rowIndex in covered.map { it.first }) row.mapIndexed { colIndex, c ->
                                if (rowIndex to colIndex in covered) if (dir == 1 to 0) '!' else if (c == '.') '#' else c else c
                            } else row
                        }
                    } to ((coords.first + (dir.first * count)) to (coords.second + (dir.second * count)))
                }.let { (grid) ->
                    grid.sumOf { row ->
                        (row.runningReduce { acc, c -> if (acc == '!') if (c == '.') '!' else c else c }
                            .takeIf { it.last() == '.' } ?: row.reversed()
                            .runningReduce { acc, c -> if (acc == '!') if (c == '.') '!' else c else c }).count { it == '!' || it == '#' }
                    }
                }
            }
        }
    })
}

private data class Data(
    val coords: Pair<Int, Int>,
    val specialRows: Set<Int>,
    val specialPts: Set<Triple<Int, IntProgression, Boolean>>,
    val skipped: Int
)

@OptIn(ExperimentalStdlibApi::class)
fun part2() {
    val input = readInput("18", "in")
    println(input.map { line ->
        line.split(' ').last().removeSurrounding("(#", ")").let { hex ->
            Step(
                when (hex.last()) {
                    '0' -> 0 to 1
                    '1' -> 1 to 0
                    '2' -> 0 to -1
                    '3' -> -1 to 0
                    else -> error(":(")
                }, hex.substring(0..<(hex.lastIndex)).hexToInt()
            )
        }
    }.let { steps ->
        steps.fold(Data(0 to 0, setOf(), emptySet(), 0)) { acc, step ->
            (acc.coords.first + (step.dir.first * step.count) to acc.coords.second + (step.dir.second * step.count)).let { newCoords ->
                if (step.dir.second == 0) Data(
                    newCoords, acc.specialRows + setOf(acc.coords.first, newCoords.first + 1), acc.specialPts + Triple(
                        acc.coords.second, if (step.dir.first == 1) acc.coords.first..(acc.coords.first + step.count)
                        else acc.coords.first.downTo(acc.coords.first - step.count), step.dir == 1 to 0
                    ), acc.skipped
                ) else Data(
                    newCoords, acc.specialRows + setOf(acc.coords.first, acc.coords.first + 1), acc.specialPts + setOf(
                        Triple(acc.coords.second + step.dir.second, acc.coords.first..acc.coords.first, false), Triple(
                            acc.coords.second + (step.dir.second * (step.count - 1)),
                            acc.coords.first..acc.coords.first,
                            false
                        )
                    ), acc.skipped + step.count - 3
                )
            }
        }.also { println(it) }.let { (_, specialRows, specialPts, skipped) ->
            specialRows.sorted().let { rows ->
                rows.slice(0..<rows.lastIndex).mapIndexed { rowIndex, row ->
                    println(rowIndex)
                    specialPts.filter { row in it.second }.sortedBy { it.first }.let { pts ->
                        (pts.takeIf { !it.last().third } ?: pts.reversed()).let { corrected ->
                            corrected.foldRightIndexed(0L) { index, pt, acc ->
                                if (pt.third) acc + abs(pt.first.toLong() - corrected[index + 1].first.toLong()) else acc + 1
                            }
                        }
                    } * (rows.getOrElse(rowIndex + 1) { rows[rowIndex] } - rows[rowIndex])
                }.sum() + skipped
            }
        }
    })
}
