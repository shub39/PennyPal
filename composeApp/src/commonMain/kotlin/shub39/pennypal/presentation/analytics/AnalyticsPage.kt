package shub39.pennypal.presentation.analytics

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.Pie
import shub39.pennypal.presentation.leadingItemShape
import shub39.pennypal.presentation.theme.AppTheme

@Composable
fun AnalyticsPage(
    state: AnalyticsState,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Column {
                Text(text = "Analytics", style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "Visualize your finances",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                )
            }

        }
    }
}

@Composable
private fun Analytic(
    modifier: Modifier = Modifier,
    shape: Shape,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = shape
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    val foodColor = Color(0xFFE57373)
    val rentColor = Color(0xFF64B5F6)
    val entColor = Color(0xFF81C784)
    val utilColor = Color(0xFFFFD54F)

//    var data by remember {
//        mutableStateOf(
//            listOf(
//                Pie(label = "Android", data = 20.0, color = Color.Red, selectedColor = Color.Green),
//                Pie(label = "Windows", data = 45.0, color = Color.Cyan, selectedColor = Color.Blue),
//                Pie(label = "Linux", data = 35.0, color = Color.Gray, selectedColor = Color.Yellow),
//            )
//        )
//    }
//
//    PieChart(
//        modifier = Modifier.size(200.dp),
//        data = data,
//        onPieClick = {
//            println("${it.label} Clicked")
//            val pieIndex = data.indexOf(it)
//            data = data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
//        },
//        selectedScale = 1.2f,
//        scaleAnimEnterSpec = spring<Float>(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        ),
//        colorAnimEnterSpec = tween(300),
//        colorAnimExitSpec = tween(300),
//        scaleAnimExitSpec = tween(300),
//        spaceDegreeAnimExitSpec = tween(300),
//        style = Pie.Style.Fill
//    )

    AppTheme {
        AnalyticsPage(
            state = AnalyticsState(
                pieChartData = listOf(
                    PieChartData("Food", 50.0, foodColor),
                    PieChartData("Rent", 20.0, rentColor),
                    PieChartData("Entertainment", 20.0, entColor),
                    PieChartData("Utilities", 10.0, utilColor)
                ),
                stackedBarData = listOf(
                    StackedBarData(
                        "Week 1", listOf(
                            PieChartData("Food", 150.0, foodColor),
                            PieChartData("Rent", 300.0, rentColor),
                            PieChartData("Entertainment", 50.0, entColor)
                        )
                    ),
                    StackedBarData(
                        "Week 2", listOf(
                            PieChartData("Food", 100.0, foodColor),
                            PieChartData("Rent", 300.0, rentColor),
                            PieChartData("Utilities", 100.0, utilColor)
                        )
                    ),
                    StackedBarData(
                        "Week 3", listOf(
                            PieChartData("Food", 200.0, foodColor),
                            PieChartData("Rent", 300.0, rentColor),
                            PieChartData("Entertainment", 150.0, entColor),
                            PieChartData("Utilities", 50.0, utilColor)
                        )
                    )
                )
            ),
        )
    }
}
