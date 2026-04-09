package shub39.pennypal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel
import shub39.pennypal.domain.AppRepository
import shub39.pennypal.domain.Category
import shub39.pennypal.domain.CategoryIcon
import shub39.pennypal.domain.TransactionType
import shub39.pennypal.presentation.home.HomeAction
import shub39.pennypal.presentation.home.HomeState

@KoinViewModel
class HomeViewModel(private val repository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state =
        _state
            .asStateFlow()
            .onStart { collectDatabase() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeState(),
            )

    private suspend fun collectDatabase() {
        val categories = repository.getCategories().first()
        if (categories.isEmpty()) {
            seedDefaultCategories()
            repository.addDummyData()
        }

        combine(repository.getTransactions(), repository.getCategories()) { transactions, categories
                ->
                val totalIncome =
                    transactions
                        .filter { it.transactionType == TransactionType.INCOME }
                        .sumOf { it.amount }
                val totalExpenses =
                    transactions
                        .filter { it.transactionType == TransactionType.EXPENSE }
                        .sumOf { it.amount }

                _state.update {
                    it.copy(
                        transactions = transactions,
                        allCategories = categories,
                        totalIncome = totalIncome,
                        totalExpenses = totalExpenses,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun seedDefaultCategories() {
        viewModelScope.launch {
            val defaultCategories =
                listOf(
                    Category(
                        name = "Food",
                        colorArgb = 0xFFFF5722.toInt(),
                        categoryIcon = CategoryIcon.FOOD,
                    ),
                    Category(
                        name = "Transport",
                        colorArgb = 0xFF03A9F4.toInt(),
                        categoryIcon = CategoryIcon.TRANSPORT,
                    ),
                    Category(
                        name = "Shopping",
                        colorArgb = 0xFFE91E63.toInt(),
                        categoryIcon = CategoryIcon.SHOPPING,
                    ),
                    Category(
                        name = "Housing",
                        colorArgb = 0xFF9C27B0.toInt(),
                        categoryIcon = CategoryIcon.HOUSING,
                    ),
                    Category(
                        name = "Bills",
                        colorArgb = 0xFFFFC107.toInt(),
                        categoryIcon = CategoryIcon.BILLS,
                    ),
                    Category(
                        name = "Entertainment",
                        colorArgb = 0xFF673AB7.toInt(),
                        categoryIcon = CategoryIcon.ENTERTAINMENT,
                    ),
                    Category(
                        name = "Health",
                        colorArgb = 0xFF4CAF50.toInt(),
                        categoryIcon = CategoryIcon.HEALTH,
                    ),
                    Category(
                        name = "Education",
                        colorArgb = 0xFF2196F3.toInt(),
                        categoryIcon = CategoryIcon.EDUCATION,
                    ),
                    Category(
                        name = "Travel",
                        colorArgb = 0xFF3F51B5.toInt(),
                        categoryIcon = CategoryIcon.TRAVEL,
                    ),
                    Category(
                        name = "Misc",
                        colorArgb = 0xFF607D8B.toInt(),
                        categoryIcon = CategoryIcon.MISC,
                    ),
                )
            defaultCategories.forEach { repository.upsertCategory(it) }
        }
    }

    fun onAction(action: HomeAction) {
        viewModelScope.launch {
            when (action) {
                is HomeAction.AddCategory -> {
                    repository.upsertCategory(action.category)
                }
                is HomeAction.AddTransaction -> {
                    repository.upsertTransaction(action.transaction)
                }
                is HomeAction.DeleteCategory -> {
                    repository.deleteCategory(action.id)
                }
                is HomeAction.DeleteIncome -> {
                    repository.deleteTransaction(action.id)
                }
                is HomeAction.DeleteTransaction -> {
                    repository.deleteTransaction(action.id)
                }
                is HomeAction.SelectRecurrence -> {
                    _state.update { it.copy(selectedRecurrence = action.recurrence) }
                }
            }
        }
    }
}
