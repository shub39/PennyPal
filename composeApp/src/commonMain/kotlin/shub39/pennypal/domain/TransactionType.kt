package shub39.pennypal.domain

enum class TransactionType {
    INCOME,
    EXPENSE;

    companion object {
        fun TransactionType.toDisplayString(): String {
            return when (this) {
                INCOME -> "Income"
                EXPENSE -> "Expense"
            }
        }
    }
}
