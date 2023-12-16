package day16

import readInput

fun main() {
    part1()
    part2()
}

private val List<String>.rec
    get() = DeepRecursiveFunction<Triple<Pair<Int, Int>, Pair<Int, Int>, Set<Pair<Pair<Int, Int>, Pair<Int, Int>>>>, Set<Pair<Pair<Int, Int>, Pair<Int, Int>>>> { (coords, dir, previous) ->
        if (coords to dir in previous) emptySet() else when (getOrNull(coords.first)?.getOrNull(coords.second)) {
            '.' -> callRecursive(
                Triple(coords.first + dir.first to coords.second + dir.second, dir, previous + (coords to dir))
            )

            '/' -> callRecursive(
                Triple(
                    coords.first - dir.second to coords.second - dir.first,
                    -dir.second to -dir.first,
                    previous + (coords to dir)
                )
            )

            '\\' -> callRecursive(
                Triple(
                    coords.first + dir.second to coords.second + dir.first,
                    dir.second to dir.first,
                    previous + (coords to dir)
                )
            )

            '|' -> if (dir.second != 0) {
                callRecursive(
                    Triple(
                        coords.first + 1 to coords.second, 1 to 0, previous + (coords to dir)
                    )
                ).let {
                    it + callRecursive(
                        Triple(
                            coords.first - 1 to coords.second, -1 to 0, it + previous + (coords to dir)
                        )
                    )
                }
            } else callRecursive(Triple(coords.first + dir.first to coords.second, dir, previous + (coords to dir)))

            '-' -> if (dir.first != 0) {
                callRecursive(
                    Triple(
                        coords.first to coords.second + 1, 0 to 1, previous + (coords to dir)
                    )
                ).let {
                    it + callRecursive(
                        Triple(
                            coords.first to coords.second - 1, 0 to -1, it + previous + (coords to dir)
                        )
                    )
                }
            } else callRecursive(
                Triple(
                    coords.first to coords.second + dir.second, dir, previous + (coords to dir)
                )
            )

            else -> emptySet()
        } + (coords to dir)
    }

fun part1() {
    val input = readInput("16", "in")
    println(input.rec(Triple(0 to 0, 0 to 1, emptySet())).map { it.first }
        .filter { it.first in input.indices && it.second in input.first().indices }.toSet().size)
}

fun part2() {
    val input = readInput("16", "in")
    println((listOf(
        (0 to 0) to (0 to 1),
        (0 to 0) to (1 to 0),
        (input.lastIndex to 0) to (-1 to 0),
        (input.lastIndex to 0) to (0 to 1),
        (0 to input.first().lastIndex) to (0 to -1),
        (0 to input.first().lastIndex) to (1 to 0),
        (input.lastIndex to input.first().lastIndex) to (-1 to 0),
        (input.lastIndex to input.first().lastIndex) to (0 to -1)
    ) + (1..<input.lastIndex).map { (it to 0) to (0 to 1) } + (1..<input.lastIndex).map { (it to input.first().lastIndex) to (0 to -1) } + (1..<input.first().lastIndex).map { (0 to it) to (1 to 0) } + (1..<input.first().lastIndex).map { (input.lastIndex to it) to (-1 to 0) }).maxOf { p ->
        input.rec(Triple(p.first, p.second, emptySet())).map { it.first }
            .filter { it.first in input.indices && it.second in input.first().indices }.toSet().size
    })
}
