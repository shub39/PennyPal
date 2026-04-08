package shub39.pennypal.presentation.settings

sealed interface SettingsAction {
    data class OnNameChange(val name: String) : SettingsAction

    data object OnSaveSettings : SettingsAction
}
