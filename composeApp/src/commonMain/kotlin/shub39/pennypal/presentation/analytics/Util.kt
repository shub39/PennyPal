package shub39.pennypal.presentation.analytics

import ir.ehsannarmani.compose_charts.models.Pie

fun PieChartData.toPie(): Pie {
    return Pie(
        label = label,
        data = data,
        color = color
    )
}