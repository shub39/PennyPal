package shub39.pennypal.presentation.auth

sealed interface AuthAction {
    data class OnNameChange(val name: String) : AuthAction
    data object OnAuthClick : AuthAction
}