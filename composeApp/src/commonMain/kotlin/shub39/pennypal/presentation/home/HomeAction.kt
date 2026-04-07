package shub39.pennypal.presentation.home

import shub39.pennypal.domain.Category
import shub39.pennypal.domain.Income
import shub39.pennypal.domain.Transaction

sealed interface HomeAction {
    data class AddIncome(val income: Income) : HomeAction

    data class DeleteIncome(val id: Long) : HomeAction

    data class AddCategory(val category: Category) : HomeAction

    data class DeleteCategory(val id: Long) : HomeAction

    data class AddTransaction(val transaction: Transaction) : HomeAction

    data class DeleteTransaction(val id: Long) : HomeAction
}
