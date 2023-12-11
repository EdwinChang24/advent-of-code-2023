package day10

import readInput

fun main() {
    part1()
    part2()
}

private tailrec fun path(
    input: List<String>, coords: Pair<Int, Int>, prevCoords: Pair<Int, Int>, acc: List<Pair<Pair<Int, Int>, Boolean?>>
): List<Pair<Pair<Int, Int>, Boolean?>> = when (input[coords.first][coords.second]) {
    'S' -> acc + (coords to null)
    '|' -> if (prevCoords.first > coords.first) path(
        input = input, coords = coords.first - 1 to coords.second, prevCoords = coords, acc = acc + (coords to null)
    ) else path(
        input = input, coords = coords.first + 1 to coords.second, prevCoords = coords, acc = acc + (coords to null)
    )

    '-' -> if (prevCoords.second > coords.second) path(
        input = input, coords = coords.first to coords.second - 1, prevCoords = coords, acc = acc + (coords to false)
    ) else path(
        input = input, coords = coords.first to coords.second + 1, prevCoords = coords, acc = acc + (coords to true)
    )

    'L' -> if (prevCoords.first == coords.first) path(
        input = input, coords = coords.first - 1 to coords.second, prevCoords = coords, acc = acc + (coords to false)
    ) else path(
        input = input, coords = coords.first to coords.second + 1, prevCoords = coords, acc = acc + (coords to true)
    )

    'J' -> if (prevCoords.first == coords.first) path(
        input = input, coords = coords.first - 1 to coords.second, prevCoords = coords, acc = acc + (coords to true)
    ) else path(
        input = input, coords = coords.first to coords.second - 1, prevCoords = coords, acc = acc + (coords to false)
    )

    '7' -> if (prevCoords.first == coords.first) path(
        input = input, coords = coords.first + 1 to coords.second, prevCoords = coords, acc = acc + (coords to null)
    ) else path(
        input = input, coords = coords.first to coords.second - 1, prevCoords = coords, acc = acc + (coords to null)
    )

    'F' -> if (prevCoords.first == coords.first) path(
        input = input, coords = coords.first + 1 to coords.second, prevCoords = coords, acc = acc + (coords to null)
    ) else path(
        input = input, coords = coords.first to coords.second + 1, prevCoords = coords, acc = acc + (coords to null)
    )

    else -> error(":(")
}

fun part1() {
    val input = readInput("10", "in")
    println(input.indexOfFirst { 'S' in it }.let { first ->
        input[first].indexOf('S').let { second ->
            when {
                input.getOrNull(first + 1)?.getOrNull(second) in listOf('|', 'J', 'L') -> first + 1 to second
                input.getOrNull(first - 1)?.getOrNull(second) in listOf('|', 'F', '7') -> first - 1 to second
                input.getOrNull(first)?.getOrNull(second + 1) in listOf('-', 'J', '7') -> first to second + 1
                input.getOrNull(first)?.getOrNull(second - 1) in listOf('-', 'F', 'L') -> first to second - 1
                else -> error(":(")
            }.let { coords ->
                path(input = input, coords = coords, prevCoords = first to second, acc = listOf()).size / 2
            }
        }
    })
}

fun part2() {
    val input = readInput("10", "in")
    println(input.indexOfFirst { 'S' in it }.let { first ->
        input[first].indexOf('S').let { second ->
            when {
                input.getOrNull(first + 1)?.getOrNull(second) in listOf('|', 'J', 'L') -> first + 1 to second
                input.getOrNull(first - 1)?.getOrNull(second) in listOf('|', 'F', '7') -> first - 1 to second
                input.getOrNull(first)?.getOrNull(second + 1) in listOf('-', 'J', '7') -> first to second + 1
                input.getOrNull(first)?.getOrNull(second - 1) in listOf('-', 'F', 'L') -> first to second - 1
                else -> error(":(")
            }.let { coords ->
                path(input = input, coords = coords, prevCoords = first to second, acc = listOf()).let { path ->
                    path.map { it.first }.let { coordsSet ->
                        tailrec fun searchDown(coords: Pair<Int, Int>, acc: Int): Int? =
                            if (coords.first !in input.indices) null else if (coords in coordsSet) acc else searchDown(
                                coords.first + 1 to coords.second, acc + 1
                            )
                        path.filter {
                            input[it.first.first][it.first.second] in "-LJ" && it.second == true
                        }.map { searchDown(it.first.first + 1 to it.first.second, 0) }
                            .takeIf { it.none { res -> res == null } } ?: path.filter {
                            input[it.first.first][it.first.second] in "-LJ" && it.second == false
                        }.map { searchDown(it.first.first + 1 to it.first.second, 0) }
                    }.sumOf { it!! }
                }
            }
        }
    })
}
