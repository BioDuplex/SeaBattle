enum class Matrix(field: MutableList<MutableList<Int>>) {
    field(
        mutableListOf(
            MutableList<Int>(10){0},
            MutableList<Int>(10){0},
            MutableList<Int>(10){0},
            MutableList<Int>(10){0},
            MutableList<Int>(10){0},
            MutableList<Int>(10){0},
            MutableList<Int>(10){0},
            MutableList<Int>(10){0},
            MutableList<Int>(10){0},
            MutableList<Int>(10){0}
        ))
}

class LastShot(val x: Int, val y: Int)

class ShipPosition(val x: Int = -1, val y: Int = -1, val rotate: Boolean = false, val length: Int = 1)
enum class DIFFICULTY {
    EASY,
    NORMAL,
    HARD
}