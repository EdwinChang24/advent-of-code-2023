package day03

import readInput

fun main() {
    part1()
    part2()
}

data class Num(val value: Int, val start: Int, val end: Int, val line: Int)

data class Star(val line: Int, val col: Int)

fun part1() {
    val input = readInput("03", "in")
    println(input.map { line -> "$line." }.let { inputPadded ->
        inputPadded.getNums().sumOf { possibleNumber ->
            with(possibleNumber) {
                if ((listOf(
                        inputPadded.getOrNull(line)?.getOrNull(start - 1) ?: '.',
                        inputPadded.getOrNull(line)?.getOrNull(end) ?: '.'
                    ) + ((start - 1)..end).map { colIndex ->
                        inputPadded.getOrNull(line - 1)?.getOrNull(colIndex) ?: '.'
                    } + ((start - 1)..end).map { colIndex ->
                        inputPadded.getOrNull(line + 1)?.getOrNull(colIndex) ?: '.'
                    }).any { c -> c != '.' && !c.isDigit() }
                ) value else 0
            }
        }
    })
}

fun part2() {
    val input = readInput("03", "in")
    println(input.map { line -> "$line." }.let { inputPadded ->
        inputPadded.getNums().let { nums ->
            inputPadded.flatMapIndexed { lineIndex: Int, s: String ->
                s.mapIndexed { col, c -> if (c == '*') Star(lineIndex, col) else null }.filterNotNull()
            }.let { stars ->
                stars.sumOf { star ->
                    nums.filter { num ->
                        (star.line == num.line && (star.col == num.start - 1 || star.col == num.end)) || (star.line == num.line - 1 && star.col in (num.start - 1)..num.end) || (star.line == num.line + 1 && star.col in (num.start - 1)..num.end)
                    }.takeIf { list -> list.size == 2 }?.let { list -> list[0].value * list[1].value } ?: 0
                }
            }
        }
    })
}

private fun List<String>.getNums() = flatMapIndexed { lineIndex, line ->
    line.mapIndexed { index, c ->
        if (((line.getOrNull(index - 1) ?: '.').isDigit() && !c.isDigit()) || (!(line.getOrNull(
                index - 1
            ) ?: '.').isDigit() && c.isDigit())
        ) index else null
    }.filterNotNull().chunked(2).map { startAndEnd ->
        Num(
            value = line.substring(startAndEnd[0]..<startAndEnd[1]).toInt(),
            start = startAndEnd[0],
            end = startAndEnd[1],
            line = lineIndex
        )
    }
}
