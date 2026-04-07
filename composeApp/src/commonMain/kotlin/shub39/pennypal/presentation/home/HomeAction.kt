package shub39.pennypal.presentation.home

sealed interface HomeAction {
    data class AddIncome(
        val amount: Float,
        val monthlyRecurring: Boolean,
        val title: String,
        val description: String?
    ) : HomeAction

    data class DeleteIncome(val id: Long) : HomeAction

//    data class AddCategory(
//    )
}