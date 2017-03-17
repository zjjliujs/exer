tailrec fun findFixPoint(x: Double = 1.0): Double
        = if (Math.abs(x - Math.cos(x)) < 0.000001) x else findFixPoint(Math.cos(x))

println ("Answer for x == Math.cos(x) is ${findFixPoint()}")
