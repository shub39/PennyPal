package shub39.pennypal.presentation.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import shub39.pennypal.domain.Category
import shub39.pennypal.domain.Income
import shub39.pennypal.domain.Transaction

@Stable
@Immutable
data class HomeState(
    val name: String = "",
    val currentCategory: Category? = null,
    val transactions: List<Transaction> = emptyList(),
    val incomes: List<Income> = emptyList(),
    val totalIncome: Double = 0.0,
    val outstandingBalance: Double = 0.0,
    val allCategories: List<Category> = emptyList()
)
