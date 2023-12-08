package day08

import readInput

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = readInput("08", "in")
    println(input.first().toList().let { instructions ->
        input.filter { '=' in it }.associate { line ->
            line.filterNot { it in "(,)" }.split(' ').let { split -> split[0] to (split[2] to split[3]) }
        }.let { map ->
            tailrec fun steps(node: String, i: Int, steps: Int): Int = if (node == "ZZZ") steps else steps(
                if (instructions[i] == 'L') map[node]!!.first else map[node]!!.second,
                (i + 1) % instructions.size,
                steps + 1
            )
            steps("AAA", 0, 0)
        }
    })
}

fun part2() {
    val input = readInput("08", "in")
    println(input.first().toList().let { instructions ->
        input.filter { '=' in it }.associate { line ->
            line.filterNot { it in "(,)" }.split(' ').let { split -> split[0] to (split[2] to split[3]) }
        }.let { map ->
            map.keys.filter { it.endsWith('A') }.map { startNode ->
                tailrec fun steps(node: String, i: Int, steps: Long): Long = if (node.endsWith('Z')) steps else steps(
                    if (instructions[i] == 'L') map[node]!!.first else map[node]!!.second,
                    (i + 1) % instructions.size,
                    steps + 1
                )
                steps(startNode, 0, 0)
            }.reduce { acc, l ->
                (acc.toBigInteger() * l.toBigInteger() / acc.toBigInteger().gcd(l.toBigInteger())).toLong()
            }
        }
    })
}
