package shub39.pennypal.presentation.settings

data class SettingsState(
    val name: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
