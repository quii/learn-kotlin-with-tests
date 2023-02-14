import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import java.lang.StringBuilder

class MethodsOnAnyTest {
    @Test
    fun `what does let do`() {
        val result = 10.let { arg ->
            print("lol")
            arg+ 2
        }
        assertThat(result, equalTo(12))
    }
    @Test
    fun `also is like let, but the result is ignored`() {
        val result: Int = 10.also { arg ->
            print("lol")
            arg+ 2
        }
        assertThat(result, equalTo(10))
    }

    @Test
    fun `let is also useful for dealing with nulls`() {
        val result = "hello".let { it.length }
        assertThat(result, equalTo(5))
    }

    @Test
    fun `with is just sugar to remove some blah dot x and blah dot y`() {
        data class Person(val name: String, val age: Int)
        val chris = Person("Chris", 30)
        val result = with(chris) {
            name + age
        }
        assertThat(result, equalTo("Chris30"))
    }

    @Test
    fun `apply binds this to the context and lets you faff around`() {
        data class Person(var name: String, var age: Int)
        val chris = Person("Chris", 30)
        chris.apply {
            name = "Christopher"
            age = 31
        }
        assertThat(chris, equalTo(Person("Christopher", 31)))
    }

    @Test
    fun `also is useful if it doesn't mutate or you wish to do side-effects`() {
        data class Person(val name: String, val age: Int)
        val chris = Person("Chris", 30)
        var totalAges = 0
        assertThat(totalAges, equalTo(0))
        chris.also {
            totalAges+= it.age // side-effect
        }
        assertThat(totalAges, equalTo(30))
    }

    @Test
    fun `better apply example, we can use it to run methods on the thing, but keep the thing`() {
        val mailer = Mailer().apply {
            from("quii@hey.com")
            to("dave@gmail.com")
            subject("Hello")
            body("How are you?")
        }
        assertThat(mailer.send(), equalTo("From: quii@hey.comTo: dave@gmail.comSubject: HelloBody: How are you?"))
    }

    @Test
    fun `better run example, like apply, but we can get the result of it`() {
        val mailer = Mailer()
        val result = mailer.run {
            from("quii@hey.com")
            to("dave@gmail.com")
            subject("Hello")
            body("How are you?")
            send()
        }
        assertThat(result, equalTo("From: quii@hey.comTo: dave@gmail.comSubject: HelloBody: How are you?"))
    }
}

class Mailer {
    val details = StringBuilder()
    fun from(address: String) {
        details.append("From: $address")
    }
    fun to(address: String) {
        details.append("To: $address")
    }
    fun subject(subject: String) {
        details.append("Subject: $subject")
    }
    fun body(body: String) {
        details.append("Body: $body")
    }
    fun send(): String {
        return details.toString()
    }
}