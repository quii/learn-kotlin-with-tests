import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class WalletKtTest {
    private val wallet = Wallet()
    @Nested
    inner class Deposit {
        @Test
        fun `wallet deposit 10 results in balance of 10`() {
            wallet.deposit(10)
            assertThat(wallet, hasBalance(10))
        }

        @Test
        fun`depositing twice accumulates the balance`() {
            wallet.deposit(10)
            wallet.deposit(10)
            assertThat(wallet, hasBalance(20))
        }
    }

}

fun hasBalance(balance: Int): Matcher<Wallet> = has(Wallet::balance, equalTo(balance))