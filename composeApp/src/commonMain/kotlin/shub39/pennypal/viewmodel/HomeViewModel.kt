package shub39.pennypal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.KoinViewModel
import shub39.pennypal.presentation.home.HomeAction
import shub39.pennypal.presentation.home.HomeState

@KoinViewModel
class HomeViewModel() : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeState()
        )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.AddCategory -> TODO()
            is HomeAction.AddTransaction -> TODO()
            is HomeAction.DeleteCategory -> TODO()
            is HomeAction.DeleteIncome -> TODO()
            is HomeAction.DeleteTransaction -> TODO()
        }
    }
}