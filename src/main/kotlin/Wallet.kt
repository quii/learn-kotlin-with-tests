class Wallet {
    var balance = 0
        get() = field
        private set(value) {
            field = value
        }

    fun deposit(i: Int) {
        balance += i
    }

}
