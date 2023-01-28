package shapes

class Square(val width: Int): Shape {
    override fun perimeter(): Int {
        return width * 4
    }

    override fun area(): Int {
        return width * width
    }
}
