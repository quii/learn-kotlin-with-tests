import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class DSLTest {
    @Test
    fun `we can create a DSL for creating meetings`() {
        val meeting = "Release Planning" meeting {
            start at 9.00
            end by 10.00
        }
        assertThat(meeting.toString(), equalTo("Release Planning meeting from 9.00 to 10.00"))
    }
}

open class MeetingTime(var time: String = "") {
    protected fun convertToString(time: Double) = String.format("%.2f", time)
}

class StartTime: MeetingTime() {
    infix fun at(theTime: Double) { time = convertToString(theTime) }
}

class EndTime: MeetingTime() {
    infix fun by(theTime: Double) { time = convertToString(theTime) }
}

data class Meeting(val title: String) {
    val start = StartTime()
    val end = EndTime()

    override fun toString() = "$title meeting from ${start.time} to ${end.time}"
}

infix fun String.meeting(block: Meeting.() -> Unit): Meeting {
    val meeting = Meeting(this)
    meeting.block()
    return meeting
}