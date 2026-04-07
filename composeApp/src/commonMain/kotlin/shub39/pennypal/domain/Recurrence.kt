package shub39.pennypal.domain

enum class Recurrence {
    NONE,
    WEEKLY,
    MONTHLY;

    companion object {
        fun Recurrence.toDisplayString(): String {
            return  when (this) {
                Recurrence.NONE -> "All"
                Recurrence.MONTHLY -> "Monthly"
                Recurrence.WEEKLY -> "Weekly"
            }
        }
    }
}