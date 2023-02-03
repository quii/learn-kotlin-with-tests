import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import org.junit.jupiter.api.Test
import shapes.Rectangle
import shapes.Shape
import shapes.Square

class ShapesKtTest {
    @Test
    fun `square`() {
        val square = Square(2)
        assertThat(square, hasPerimeter(8))
        assertThat(square, hasArea(4))
    }

    @Test
    fun `rectangle`() {
        val rectangle = Rectangle(2, 3)
        assertThat(rectangle, hasPerimeter(10))
        assertThat(rectangle, hasArea(6))
    }
}

fun hasArea(area: Int): Matcher<Shape> = has(Shape::area, equalTo(area))

fun hasPerimeter(perimeter: Int): Matcher<Shape> = has(Shape::perimeter, equalTo(perimeter))