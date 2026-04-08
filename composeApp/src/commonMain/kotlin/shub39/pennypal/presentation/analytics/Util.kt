package shub39.pennypal.presentation.analytics

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie
import kotlin.math.roundToInt

fun PieChartData.toPie(): Pie {
    return Pie(label = "(${data.roundToInt()}%) $label", data = data, color = color)
}

fun IncomeVsExpenseData.toBars(): Bars {
    return Bars(
        label = label,
        values =
            listOf(
                Bars.Data(
                    label = "Income",
                    value = income,
                    color = Brush.horizontalGradient(listOf(Color(0xFF0B7AD4), Color(0xFF4FAEFF))),
                ),
                Bars.Data(
                    label = "Expense",
                    value = expense,
                    color = Brush.horizontalGradient(listOf(Color(0xFFB83B18), Color(0xFFFA6539))),
                ),
            ),
    )
}

fun ExpenseCategoriesComparison.toLine(): Line {
    return Line(
        label = label,
        values = values,
        color = SolidColor(color),
        curvedEdges = true,
        dotProperties =
            DotProperties(
                enabled = true,
                color = SolidColor(Color.White),
                strokeWidth = 4.dp,
                radius = 7.dp,
                strokeColor = SolidColor(color),
            ),
    )
}
