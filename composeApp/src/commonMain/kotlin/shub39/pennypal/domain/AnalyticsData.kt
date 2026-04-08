package shub39.pennypal.domain

import kotlinx.coroutines.flow.Flow

data class CategoryAmount(val name: String, val amount: Double, val colorArgb: Int)

data class MonthlyIncomeExpense(val monthName: String, val income: Double, val expense: Double)

data class CategoryMonthlyTrend(val name: String, val values: List<Double>, val colorArgb: Int)

data class AnalyticsData(
    val avgMonthlyExpenditure: Double,
    val avgMonthlyIncome: Double,
    val expensePieChartData: List<CategoryAmount>,
    val incomePieChartData: List<CategoryAmount>,
    val incomeVsExpenseData: List<MonthlyIncomeExpense>,
    val expenseCategoriesComparison: List<CategoryMonthlyTrend>,
)

interface AnalyticsDataRepo {
    fun getAnalyticsData(): Flow<AnalyticsData>
}
