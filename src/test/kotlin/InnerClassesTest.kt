import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class InnerClassesTest {

    class DirectoryExplorer(val user: String) {
        inner class PermissionCheck {
            fun hasPermission() = user == "Chris"
        }

        fun walkDirectories(): List<String> =
            if (!PermissionCheck().hasPermission()) emptyList() else listOf("lib", "usr")
    }

    @Test fun `chris has permission to explore`() {
        val chrisFS = DirectoryExplorer("Chris")
        expectThat(chrisFS.walkDirectories()).isEqualTo(listOf("lib", "usr"))
    }

    @Test fun `other dorks dont`() {
        val johnFS = DirectoryExplorer("John")
        expectThat(johnFS.walkDirectories()).isEqualTo(emptyList())
    }
}