package shub39.pennypal.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.KoinViewModel
import shub39.pennypal.domain.AnalyticsDataRepo
import shub39.pennypal.presentation.analytics.AnalyticsState
import shub39.pennypal.presentation.analytics.ExpenseCategoriesComparison
import shub39.pennypal.presentation.analytics.IncomeVsExpenseData
import shub39.pennypal.presentation.analytics.PieChartData

@KoinViewModel
class AnalyticsViewModel(private val analyticsDataRepo: AnalyticsDataRepo) : ViewModel() {

    val state: StateFlow<AnalyticsState> =
        analyticsDataRepo
            .getAnalyticsData()
            .map { data ->
                AnalyticsState(
                    avgMonthlyExpenditure = data.avgMonthlyExpenditure,
                    avgMonthlyIncome = data.avgMonthlyIncome,
                    expensePieChartData =
                        data.expensePieChartData.map {
                            PieChartData(
                                label = it.name,
                                data = it.amount,
                                color = Color(it.colorArgb),
                            )
                        },
                    incomePieChartData =
                        data.incomePieChartData.map {
                            PieChartData(
                                label = it.name,
                                data = it.amount,
                                color = Color(it.colorArgb),
                            )
                        },
                    incomeVsExpenseData =
                        data.incomeVsExpenseData.map {
                            IncomeVsExpenseData(
                                label = it.monthName,
                                income = it.income,
                                expense = it.expense,
                            )
                        },
                    expenseCategoriesComparison =
                        data.expenseCategoriesComparison.map {
                            ExpenseCategoriesComparison(
                                label = it.name,
                                values = it.values,
                                color = Color(it.colorArgb),
                            )
                        },
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = AnalyticsState(),
            )
}
