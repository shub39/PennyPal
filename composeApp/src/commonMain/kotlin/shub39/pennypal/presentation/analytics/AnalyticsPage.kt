package shub39.pennypal.presentation.analytics

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.materialkolor.ktx.blend
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.RowChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.VerticalIndicatorProperties
import org.jetbrains.compose.resources.vectorResource
import pennypal.composeapp.generated.resources.Res
import pennypal.composeapp.generated.resources.trend_down
import pennypal.composeapp.generated.resources.trend_up
import shub39.pennypal.presentation.endItemShape
import shub39.pennypal.presentation.leadingItemShape
import shub39.pennypal.presentation.theme.AppTheme

@Composable
fun AnalyticsPage(state: AnalyticsState, modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Analytics", style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "Visualize your finances",
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                )
            }

            LazyColumn(
                modifier =
                    Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentPadding = PaddingValues(top = 16.dp, bottom = 60.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                item {
                    // avg monthly expense
                    ListItem(
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        headlineContent = { Text(text = "₹ ${state.avgMonthlyIncome}") },
                        supportingContent = { Text(text = "Your average monthly income") },
                        leadingContent = {
                            Icon(
                                imageVector = vectorResource(Res.drawable.trend_up),
                                contentDescription = null,
                            )
                        },
                        modifier =
                            Modifier.background(
                                brush =
                                    Brush.horizontalGradient(
                                        0f to MaterialTheme.colorScheme.surfaceContainerHighest,
                                        0.7f to
                                            Color(0xFF0B7AD4)
                                                .blend(
                                                    MaterialTheme.colorScheme
                                                        .surfaceContainerHighest
                                                ),
                                        1f to Color(0xFF4FAEFF),
                                    ),
                                shape = leadingItemShape(),
                            ),
                    )
                }

                item {
                    ListItem(
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        headlineContent = { Text(text = "₹ ${state.avgMonthlyExpenditure}") },
                        supportingContent = { Text(text = "Your average monthly expenditure") },
                        leadingContent = {
                            Icon(
                                imageVector = vectorResource(Res.drawable.trend_down),
                                contentDescription = null,
                            )
                        },
                        modifier =
                            Modifier.background(
                                brush =
                                    Brush.horizontalGradient(
                                        0f to MaterialTheme.colorScheme.surfaceContainerHighest,
                                        0.7f to
                                            Color(0xFFB83B18)
                                                .blend(
                                                    MaterialTheme.colorScheme
                                                        .surfaceContainerHighest
                                                ),
                                        1f to Color(0xFFFA6539),
                                    ),
                                shape = endItemShape(),
                            ),
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    val expenseCategoriesComparison by remember {
                        mutableStateOf(state.expenseCategoriesComparison.map { it.toLine() })
                    }

                    Analytic(shape = leadingItemShape()) {
                        Text(
                            text = "Expense Categories Comparison",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        LineChart(
                            modifier = Modifier.padding(horizontal = 8.dp).heightIn(max = 300.dp),
                            data = expenseCategoriesComparison,
                            labelHelperProperties =
                                LabelHelperProperties(
                                    textStyle =
                                        MaterialTheme.typography.labelMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                ),
                            indicatorProperties =
                                HorizontalIndicatorProperties(
                                    textStyle =
                                        MaterialTheme.typography.labelMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                ),
                            labelProperties =
                                LabelProperties(
                                    enabled = true,
                                    textStyle =
                                        MaterialTheme.typography.labelMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface
                                        ),
                                ),
                        )
                    }
                }

                item {
                    val incomeVsExpenseData by remember {
                        mutableStateOf(state.incomeVsExpenseData.map { it.toBars() })
                    }
                    // income vs expenses
                    Analytic(shape = endItemShape()) {
                        Text(
                            text = "Expenses Vs Income",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        RowChart(
                            modifier = Modifier.padding(horizontal = 8.dp).heightIn(max = 300.dp),
                            data = incomeVsExpenseData,
                            barProperties =
                                BarProperties(
                                    cornerRadius = Bars.Data.Radius.Circular(15.dp),
                                    thickness = 20.dp,
                                    spacing = 3.dp,
                                ),
                            labelHelperProperties =
                                LabelHelperProperties(
                                    textStyle =
                                        MaterialTheme.typography.labelMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                ),
                            indicatorProperties =
                                VerticalIndicatorProperties(
                                    textStyle =
                                        MaterialTheme.typography.labelMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                ),
                            labelProperties =
                                LabelProperties(
                                    enabled = true,
                                    textStyle =
                                        MaterialTheme.typography.labelMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface
                                        ),
                                ),
                            animationSpec =
                                spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow,
                                ),
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    var incomePieChart by remember {
                        mutableStateOf(state.incomePieChartData.map { it.toPie() })
                    }

                    // Income Pie Chart
                    Analytic(shape = leadingItemShape()) {
                        Text(
                            text = "Income Distribution",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        PieChart(
                            modifier = Modifier.fillMaxWidth().size(200.dp),
                            data = incomePieChart,
                            labelHelperProperties =
                                LabelHelperProperties(
                                    textStyle =
                                        MaterialTheme.typography.labelMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                ),
                            scaleAnimEnterSpec =
                                spring<Float>(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow,
                                ),
                            colorAnimEnterSpec = tween(300),
                            colorAnimExitSpec = tween(300),
                            scaleAnimExitSpec = tween(300),
                            spaceDegreeAnimExitSpec = tween(300),
                            onPieClick = {
                                val pieIndex = incomePieChart.indexOf(it)
                                incomePieChart =
                                    incomePieChart.mapIndexed { mapIndex, pie ->
                                        pie.copy(selected = pieIndex == mapIndex)
                                    }
                            },
                        )
                    }
                }
                item {
                    var expensePieChart by remember {
                        mutableStateOf(state.expensePieChartData.map { it.toPie() })
                    }

                    // expense pie chart
                    Analytic(shape = endItemShape()) {
                        Text(
                            text = "Expense Distribution",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.fillMaxWidth(),
                        )

                        PieChart(
                            modifier = Modifier.fillMaxWidth().size(200.dp),
                            data = expensePieChart,
                            labelHelperProperties =
                                LabelHelperProperties(
                                    textStyle =
                                        MaterialTheme.typography.labelMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                ),
                            scaleAnimEnterSpec =
                                spring<Float>(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow,
                                ),
                            colorAnimEnterSpec = tween(300),
                            colorAnimExitSpec = tween(300),
                            scaleAnimExitSpec = tween(300),
                            spaceDegreeAnimExitSpec = tween(300),
                            onPieClick = {
                                val pieIndex = expensePieChart.indexOf(it)
                                expensePieChart =
                                    expensePieChart.mapIndexed { mapIndex, pie ->
                                        pie.copy(selected = pieIndex == mapIndex)
                                    }
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Analytic(
    modifier: Modifier = Modifier,
    shape: Shape,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(modifier = modifier, shape = shape) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content,
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

    AppTheme {
        AnalyticsPage(
            state =
                AnalyticsState(
                    avgMonthlyIncome = 10000.0,
                    avgMonthlyExpenditure = 10000.0,
                    expenseCategoriesComparison =
                        listOf(
                            ExpenseCategoriesComparison(
                                label = "Food",
                                listOf(40.0, 50.0, 80.0),
                                foodColor,
                            ),
                            ExpenseCategoriesComparison(
                                label = "Rent",
                                listOf(20.0, 50.0, 90.0),
                                rentColor,
                            ),
                        ),
                    expensePieChartData =
                        listOf(
                            PieChartData("Food", 50.0, foodColor),
                            PieChartData("Rent", 20.0, rentColor),
                            PieChartData("Entertainment", 20.0, entColor),
                            PieChartData("Utilities", 10.0, utilColor),
                        ),
                    incomePieChartData =
                        listOf(
                            PieChartData("Job 1", 50.0, foodColor),
                            PieChartData("BMC", 20.0, rentColor),
                            PieChartData("Investments", 30.0, entColor),
                        ),
                    incomeVsExpenseData =
                        listOf(
                            IncomeVsExpenseData(label = "Jan", income = 5000.0, expense = 6000.0),
                            IncomeVsExpenseData(label = "Feb", income = 7000.0, expense = 8000.0),
                            IncomeVsExpenseData(label = "Mar", income = 7000.0, expense = 6000.0),
                            IncomeVsExpenseData(label = "Apr", income = 2000.0, expense = 1000.0),
                        ),
                )
        )
    }
}
