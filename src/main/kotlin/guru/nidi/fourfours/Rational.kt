package guru.nidi.fourfours

class Rational(n: Int, d: Int) : Comparable<Rational> {
    val num: Int
    val denom: Int

    init {
        if (n == 0) {
            this.num = 0
            this.denom = 1
        } else {
            val t = gcd(n, d)
            this.num = n / t
            this.denom = d / t
        }
    }

    constructor(value: Int) : this(value, 1)

    private fun gcd(a: Int, b: Int): Int {
        var n = Math.abs(a)
        var m = Math.abs(b)
        while (m != 0) {
            val t = m
            m = n % m
            n = t
        }
        return n
    }

    operator fun plus(r: Rational): Rational? {
        val n1 = multiplyExact(num, r.denom)
        val n2 = multiplyExact(r.num, denom)
        val d = multiplyExact(denom, r.denom)
        return if (n1 == null || n2 == null || d == null) null else Rational(n1 + n2, d)
    }

    operator fun minus(r: Rational): Rational? {
        val n1 = multiplyExact(num, r.denom)
        val n2 = multiplyExact(r.num, denom)
        val d = multiplyExact(denom, r.denom)
        return if (n1 == null || n2 == null || d == null) null else Rational(n1 - n2, d)
    }

    operator fun times(r: Rational): Rational? {
        val n = multiplyExact(num, r.num)
        val d = multiplyExact(denom, r.denom)
        return if (n == null || d == null) null else Rational(n, d)
    }

    operator fun div(r: Rational): Rational? {
        val n = multiplyExact(num, r.denom)
        val d = multiplyExact(denom, r.num)
        return if (n == null || d == null) null else Rational(n, d)
    }

    operator fun compareTo(n: Double): Int {
        val v = toDouble()
        return if (v > n) 1 else if (v < n) -1 else 0
    }

    private fun multiplyExact(x: Int, y: Int): Int ? {
        val r = x.toLong() * y.toLong()
        if (r.toInt().toLong() != r) {
            return null
        }
        return r.toInt()
    }


    fun toDouble() = num.toDouble() / denom

    fun inv() = Rational(denom, num)
    fun isZero() = num == 0

    fun pow(r: Rational): Rational? {
        val n = toInt(Math.pow(num.toDouble(), r.toDouble()))
        val d = toInt(Math.pow(denom.toDouble(), r.toDouble()))
        return if (n == null || n === 0 || d == null || d == 0) null else Rational(n, d)
    }

    private fun isInt(d: Double) = Math.abs(d) < Int.MAX_VALUE && Math.abs(d % 1) < .000001
    private fun toInt(d: Double): Int? = if (isInt(d)) Math.round(d).toInt() else null

    fun fac(): Rational {
        if (!isNatural()) {
            throw IllegalStateException("Must be natural")
        }
        var s = 1
        var m = 2
        while (m <= num) {
            s *= m
            m++
        }
        return Rational(s)
    }

    fun isNatural(): Boolean = denom == 1

    override fun compareTo(other: Rational): Int {
        return toDouble().compareTo(other.toDouble())
    }

    override fun toString() = if (isNatural()) "$num" else "($num/$denom)"

    override fun equals(other: Any?): Boolean {
        if (other !is Rational) {
            return false
        }
        return num == other.num && denom == other.denom
    }

    override fun hashCode(): Int {
        return (num + 31 * denom).toInt()
    }
}