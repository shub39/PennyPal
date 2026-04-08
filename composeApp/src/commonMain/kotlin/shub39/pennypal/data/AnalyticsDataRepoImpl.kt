package shub39.pennypal.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.annotation.Single
import shub39.pennypal.data.database.CategoryDao
import shub39.pennypal.data.database.TransactionDao
import shub39.pennypal.domain.*

@Single(binds = [AnalyticsDataRepo::class])
class AnalyticsDataRepoImpl(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
) : AnalyticsDataRepo {
    override fun getAnalyticsData(): Flow<AnalyticsData> {
        return combine(transactionDao.getAllTransactions(), categoryDao.getAllCategories()) {
            transactions,
            categories ->
            val categoryMap = categories.associateBy { it.id }
            val timeZone = TimeZone.currentSystemDefault()

            val transactionsWithMonth =
                transactions.map {
                    val localDateTime = it.date.toLocalDateTime(timeZone)
                    val monthYear = "${localDateTime.month.name} ${localDateTime.year}"
                    it to monthYear
                }

            val incomeTransactions =
                transactions.filter { it.transactionType == TransactionType.INCOME }
            val expenseTransactions =
                transactions.filter { it.transactionType == TransactionType.EXPENSE }

            // Avg Monthly Income
            val monthlyIncome =
                transactionsWithMonth
                    .filter { it.first.transactionType == TransactionType.INCOME }
                    .groupBy { it.second }
                    .mapValues { it.value.sumOf { t -> t.first.amount } }
            val avgMonthlyIncome =
                if (monthlyIncome.isNotEmpty()) monthlyIncome.values.average() else 0.0

            // Avg Monthly Expense
            val monthlyExpense =
                transactionsWithMonth
                    .filter { it.first.transactionType == TransactionType.EXPENSE }
                    .groupBy { it.second }
                    .mapValues { it.value.sumOf { t -> t.first.amount } }
            val avgMonthlyExpense =
                if (monthlyExpense.isNotEmpty()) monthlyExpense.values.average() else 0.0

            // Pie Chart Data (All time)
            val incomePieChartData =
                incomeTransactions
                    .groupBy { it.categoryId }
                    .map { (catId, trans) ->
                        val category = categoryMap[catId]
                        CategoryAmount(
                            name = category?.name ?: "Unknown",
                            amount = trans.sumOf { it.amount },
                            colorArgb = category?.colorArgb ?: 0,
                        )
                    }

            val expensePieChartData =
                expenseTransactions
                    .groupBy { it.categoryId }
                    .map { (catId, trans) ->
                        val category = categoryMap[catId]
                        CategoryAmount(
                            name = category?.name ?: "Unknown",
                            amount = trans.sumOf { it.amount },
                            colorArgb = category?.colorArgb ?: 0,
                        )
                    }

            // Income vs Expense Data (Monthly)
            val sortedMonths =
                transactionsWithMonth
                    .map { it.first.date to it.second }
                    .sortedBy { it.first }
                    .map { it.second }
                    .distinct()

            val incomeVsExpenseData =
                sortedMonths.map { month ->
                    MonthlyIncomeExpense(
                        monthName = month.take(3),
                        income = monthlyIncome[month] ?: 0.0,
                        expense = monthlyExpense[month] ?: 0.0,
                    )
                }

            // Expense Categories Comparison (Trend)
            val expenseCategories =
                expenseTransactions.mapNotNull { categoryMap[it.categoryId] }.distinctBy { it.id }
            val expenseCategoriesComparison =
                expenseCategories.map { category ->
                    val values =
                        sortedMonths.map { month ->
                            transactionsWithMonth
                                .filter {
                                    it.second == month &&
                                        it.first.categoryId == category.id &&
                                        it.first.transactionType == TransactionType.EXPENSE
                                }
                                .sumOf { it.first.amount }
                        }
                    CategoryMonthlyTrend(
                        name = category.name,
                        values = values,
                        colorArgb = category.colorArgb,
                    )
                }

            AnalyticsData(
                avgMonthlyExpenditure = avgMonthlyExpense,
                avgMonthlyIncome = avgMonthlyIncome,
                expensePieChartData = expensePieChartData,
                incomePieChartData = incomePieChartData,
                incomeVsExpenseData = incomeVsExpenseData,
                expenseCategoriesComparison = expenseCategoriesComparison,
            )
        }
    }
}
