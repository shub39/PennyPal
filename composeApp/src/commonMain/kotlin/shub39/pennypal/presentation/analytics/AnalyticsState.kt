package shub39.pennypal.presentation.analytics

import androidx.compose.ui.graphics.Color

data class PieChartData(val label: String, val data: Double, val color: Color)

data class IncomeVsExpenseData(
    val label: String, // month name
    val income: Double,
    val expense: Double,
)

data class ExpenseCategoriesComparison(
    val label: String, // category name,
    val values: List<Double>,
    val color: Color,
)

data class AnalyticsState(
    val avgMonthlyExpenditure: Double = 0.0,
    val avgMonthlyIncome: Double = 0.0,
    val expensePieChartData: List<PieChartData> = emptyList(),
    val incomePieChartData: List<PieChartData> = emptyList(),
    val incomeVsExpenseData: List<IncomeVsExpenseData> = emptyList(),
    val expenseCategoriesComparison: List<ExpenseCategoriesComparison> = emptyList(),
)
