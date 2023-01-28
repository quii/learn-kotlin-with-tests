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
        assertThat(Square(2), hasPerimeter(8))
        assertThat(Square(2), hasArea(4))

    }

    @Test
    fun `rectangle`() {
        assertThat(Rectangle(2, 3), hasPerimeter(10))
        assertThat(Rectangle(2, 3), hasArea(6))
    }
}

fun hasArea(area: Int): Matcher<Shape> = has(Shape::area, equalTo(area))

fun hasPerimeter(perimeter: Int): Matcher<Shape> = has(Shape::perimeter, equalTo(perimeter))