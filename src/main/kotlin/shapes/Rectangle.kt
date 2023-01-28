package shapes

class Rectangle(val width: Int, val height: Int): Shape {
    override fun perimeter(): Int {
        return width * 2 + height * 2
    }

    override fun area(): Int {
        return width * height
    }

}
