import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.throws
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ClassTest {

    class Car(val yearOfMake: Int, var colour: String = "Red", registration: String) {
        var fuelLevel = 100
        var registrationNumber = registration
            set(value) {
                if (value.length !=6) {
                    throw IllegalArgumentException("Registration number must be 6 characters")
                }
                field = value
            }

        init {
            if(colour=="Blue") {
                fuelLevel = 50
            }
        }

        companion object {
            fun carsGoVroom() = "Vroom"
        }
    }

    @Test
    fun `read only properties are done with val`() {
        val car = Car(2019, registration = "ABC123")
        assertThat(car.yearOfMake, equalTo(2019))
    }

    @Test
    fun `you can set read-write properties with var, and give them default values`(){
        val car = Car(2019, registration = "ABC123")
        assertThat(car.colour, equalTo("Red"))
        car.colour = "Blue"
        assertThat(car.colour, equalTo("Blue"))
    }

    @Test
    fun `you can create setters with validation`(){
        val car = Car(2019, registration = "ABC123")
        assertThat(car.registrationNumber, equalTo("ABC123"))
        assertThrows<java.lang.IllegalArgumentException> { car.registrationNumber="" }
    }

    @Test
    fun `you can make properties outside the constructor too of course`() {
        val car = Car(2019, registration = "ABC123")
        assertThat(car.fuelLevel, equalTo(100))
    }

    @Test
    fun `init blocks are run when the object is created`() {
        val car = Car(2019, "Blue", registration = "ABC123")
        // "blue cars have less fuel, for reasons"
        assertThat(car.fuelLevel, equalTo(50))
    }

    @Test
    fun `companion objects are like static methods`() {
        assertThat(Car.carsGoVroom(), equalTo("Vroom"))
    }
}