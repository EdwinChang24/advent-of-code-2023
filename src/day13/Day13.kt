package day13

import readInput

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = readInput("13", "in")
    println(input.joinToString("\n").split("\n\n").map { it.lines() }.sumOf { pattern ->
        (1..<pattern.size).firstOrNull { rowIndex ->
            pattern.slice(0..<rowIndex)
                .mapIndexed { index, s -> s == pattern.getOrElse(rowIndex - index + rowIndex - 1) { s } }.all { it }
        }?.times(100)
            ?: (List(pattern.first().length) { i -> List(pattern.size) { j -> pattern[j][i] }.joinToString("") }).let { transposed ->
                (1..<transposed.size).first { rowIndex ->
                    transposed.slice(0..<rowIndex)
                        .mapIndexed { index, s -> s == transposed.getOrElse(rowIndex - index + rowIndex - 1) { s } }
                        .all { it }
                }
            }
    })
}

fun part2() {
    val input = readInput("13", "in")
    println(input.joinToString("\n").split("\n\n").map { it.lines() }.let { patterns ->
        patterns.map { pattern ->
            (1..<pattern.size).firstOrNull { rowIndex ->
                pattern.slice(0..<rowIndex)
                    .mapIndexed { index, s -> s == pattern.getOrElse(rowIndex - index + rowIndex - 1) { s } }.all { it }
            }?.times(100)
                ?: (List(pattern.first().length) { i -> List(pattern.size) { j -> pattern[j][i] }.joinToString("") }).let { transposed ->
                    (1..<transposed.size).first { rowIndex ->
                        transposed.slice(0..<rowIndex)
                            .mapIndexed { index, s -> s == transposed.getOrElse(rowIndex - index + rowIndex - 1) { s } }
                            .all { it }
                    }
                }
        }.let { originalLines ->
            patterns.mapIndexed { patternIndex, pattern ->
                pattern.indices.map { r ->
                    pattern.first().indices.map { c ->
                        pattern.mapIndexed { index, s ->
                            if (index == r) s.replaceRange(c..c, if (s[c] == '.') "#" else ".") else s
                        }.let { newPattern ->
                            (1..<newPattern.size).firstOrNull { rowIndex ->
                                rowIndex * 100 != originalLines[patternIndex] && newPattern.slice(0..<rowIndex)
                                    .mapIndexed { index, s -> s == newPattern.getOrElse(rowIndex - index + rowIndex - 1) { s } }
                                    .all { it }
                            }?.times(100) ?: (List(pattern.first().length) { i ->
                                List(newPattern.size) { j -> newPattern[j][i] }.joinToString("")
                            }).let { transposed ->
                                (1..<transposed.size).firstOrNull { rowIndex ->
                                    rowIndex != originalLines[patternIndex] && transposed.slice(0..<rowIndex)
                                        .mapIndexed { index, s -> s == transposed.getOrElse(rowIndex - index + rowIndex - 1) { s } }
                                        .all { it }
                                }
                            }
                        }
                    }.firstOrNull { it != null }
                }.first { it != null }
            }.sumOf { it!! }
        }
    })
}
