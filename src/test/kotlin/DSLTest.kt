import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isTrue

class DSLTest {
    @Test
    fun `simple infix demo`() {
        infix fun String.shouldEqual(x: String) = x == this
        expectThat("poo" shouldEqual "poo").isTrue()
    }

    @Test
    fun `we can create a DSL for creating meetings using lambda extensions`() {
        val meeting = "10% time demos" meeting {
            start at 16.00
            end by 17.00
        }
        assertThat(meeting.toString(), equalTo("10% time demos meeting from 16.00 to 17.00"))
    }
}

infix fun String.meeting(block: Meeting.() -> Unit): Meeting {
    val meeting = Meeting(this)
    meeting.block()
    return meeting
}

data class Meeting(val title: String) {
    val start = StartTime()

    val end = EndTime()
    override fun toString() = "$title meeting from $start to $end"
}

open class MeetingTime(var time: String = "") {
    protected fun convertToString(time: Double) = String.format("%.2f", time)
    override fun toString() = time
}

class StartTime: MeetingTime() {
    infix fun at(theTime: Double) { time = convertToString(theTime) }
}

class EndTime: MeetingTime() {
    infix fun by(theTime: Double) { time = convertToString(theTime) }
}
