package shub39.pennypal.domain

enum class AppTheme {
    LIGHT,
    DARK,
    SYSTEM;

    companion object {
        fun AppTheme.toDisplayString(): String {
            return when (this) {
                AppTheme.LIGHT -> "Light"
                AppTheme.DARK -> "Dark"
                AppTheme.SYSTEM -> "System"
            }
        }
    }
}
