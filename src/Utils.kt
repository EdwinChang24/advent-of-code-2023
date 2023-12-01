import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: String, name: String) = Path("src/day$day/$name.txt").readLines()
