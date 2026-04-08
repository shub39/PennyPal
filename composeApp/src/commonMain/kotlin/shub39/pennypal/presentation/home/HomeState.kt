package shub39.pennypal.presentation.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import shub39.pennypal.domain.Category
import shub39.pennypal.domain.Recurrence
import shub39.pennypal.domain.Transaction

@Stable
@Immutable
data class HomeState(
    val name: String = "",
    val transactions: List<Transaction> = emptyList(),
    val totalExpenses: Double = 0.0,
    val totalIncome: Double = 0.0,
    val allCategories: List<Category> = emptyList(),
    val selectedRecurrence: Recurrence = Recurrence.NONE,
)
