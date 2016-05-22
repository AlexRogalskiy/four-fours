package guru.nidi.fourfours

import java.io.File
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    for(i in 1..9) {
        run(i, 4)
    }
}

fun run(digit: Int, depth: Int) {
    File("target/result-$digit-$depth.txt").printWriter().use { out ->

        val time = measureTimeMillis {
            val res = Builder(digit, 40000, depth).build()

            var level = 0
            val miss = mutableListOf<Int>()
            val levels = mutableListOf<Int>()
            for (i in 0..40000) {
                val tree = res.get(Rational(i))
                if (tree == null) {
                    miss.add(i)
                } else {
                    out.println("$i: $tree")
                    if (tree.level > level) {
                        level = tree.level
                        levels.add(i)
                    }
                }
            }

            out.println("Next Levels at: $levels")
            out.println("Misses (${miss.size}): $miss")
        }
        out.println("time: ${time}ms")
    }
//    Thread.sleep(100000000000)
}
