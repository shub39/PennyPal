package shub39.pennypal.presentation.auth

data class AuthState(
    val name: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)