package shub39.pennypal.presentation.analytics

import androidx.compose.ui.graphics.Color

data class PieChartData(
    val label: String,
    val data: Double,
    val color: Color
)

data class StackedBarData(
    val label: String,
    val segments: List<PieChartData>
)

data class AnalyticsState(
    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val balanceRemaining: Double = 0.0,
    val pieChartData: List<PieChartData> = emptyList(),
    val stackedBarData: List<StackedBarData> = emptyList()
)
