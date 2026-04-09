package shub39.pennypal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel
import shub39.pennypal.domain.AppRepository

@KoinViewModel
class SettingsViewModel(private val repository: AppRepository) : ViewModel() {
    val isDataEmpty: StateFlow<Boolean> =
        repository
            .getTransactions()
            .map { it.isEmpty() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = true,
            )

    fun onDeleteData() {
        viewModelScope.launch { repository.deleteAllData() }
    }

    fun onAddData() {
        viewModelScope.launch { repository.addDummyData() }
    }
}
