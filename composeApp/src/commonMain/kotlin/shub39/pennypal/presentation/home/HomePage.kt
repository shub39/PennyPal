package shub39.pennypal.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TonalToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.vectorResource
import pennypal.composeapp.generated.resources.Res
import pennypal.composeapp.generated.resources.edit
import pennypal.composeapp.generated.resources.wallet
import shub39.pennypal.domain.Category
import shub39.pennypal.domain.CategoryIcon
import shub39.pennypal.domain.CategoryIcon.Companion.toDrawable
import shub39.pennypal.domain.Income
import shub39.pennypal.domain.Recurrence
import shub39.pennypal.domain.Recurrence.Companion.toDisplayString
import shub39.pennypal.domain.Transaction
import shub39.pennypal.presentation.detachedItemShape
import shub39.pennypal.presentation.endItemShape
import shub39.pennypal.presentation.leadingItemShape
import shub39.pennypal.presentation.middleItemShape
import shub39.pennypal.presentation.theme.AppTheme
import kotlin.math.roundToInt
import kotlin.time.Clock

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomePage(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box {
            Column(
                modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // greeting
                Column {
                    Text(
                        text = "Hey, ${state.name}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Manage your expenses",
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }

                // income card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                0f to Color(0xffe8d1b2),
                                0.3f to Color(0xffa0c6aa),
                                0.7f to Color(0xff6fc0a6),
                                1f to Color(0xff40b9a1)
                            ),
                            shape = MaterialTheme.shapes.large
                        )
                        .clip(MaterialTheme.shapes.large)
                        .clickable {
                            TODO()
                        }
                ) {
                    Box(
                        modifier = Modifier.matchParentSize()
                            .background(
                                color = MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                                shape = MaterialTheme.shapes.large
                            )
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = vectorResource(Res.drawable.wallet),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Your Wallet",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }

                            Icon(
                                imageVector = vectorResource(Res.drawable.edit),
                                contentDescription = null
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Balance Left",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                                )
                                Text(
                                    text = "₹ ${state.outstandingBalance.roundToInt()}",
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black)
                                )
                            }


                            Column {
                                Text(
                                    text = "Total Income",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                                )
                                Text(
                                    text = "₹ ${state.totalIncome.roundToInt()}",
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }

                // your transactions
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column {
                        Text(
                            text = "Your transactions",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            Recurrence.entries.forEachIndexed { index, recurrence ->
                                TonalToggleButton(
                                    checked = state.selectedRecurrence == recurrence,
                                    onCheckedChange = { TODO() },
                                    shapes = when (index) {
                                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                        Recurrence.entries.size - 1 -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = recurrence.toDisplayString())
                                }
                            }
                        }
                    }

                    val transactionItems = state.transactions.filter {
                        when (state.selectedRecurrence) {
                            Recurrence.NONE -> it.recurrence == Recurrence.NONE
                            Recurrence.WEEKLY -> it.recurrence == Recurrence.WEEKLY
                            Recurrence.MONTHLY -> it.recurrence == Recurrence.MONTHLY
                        }
                    }
                    val itemsGroupedByCategory = transactionItems
                        .groupBy { item -> state.allCategories.find { it.id == item.categoryId }!! }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                        contentPadding = PaddingValues(top = 8.dp, bottom = 60.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        itemsGroupedByCategory.forEach { group ->
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = vectorResource(group.key.categoryIcon.toDrawable()),
                                        contentDescription = null
                                    )
                                    Text(
                                        text = group.key.name,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    group.value.forEachIndexed { index, item ->
                                        val shape = when {
                                            group.value.size == 1 -> detachedItemShape()
                                            index == 0 -> leadingItemShape()
                                            index == group.value.size - 1 -> endItemShape()
                                            else -> middleItemShape()
                                        }

                                        Card(
                                            modifier = Modifier,
                                            shape = shape
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column {
                                                    Text(
                                                        text = "₹ ${item.amount.roundToInt()}",
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                    item.note?.let {
                                                        Text(
                                                            text = it,
                                                            style = MaterialTheme.typography.labelMedium
                                                        )
                                                    }
                                                }

                                                Text(
                                                    text = item.date.toLocalDateTime(TimeZone.UTC).date.format(
                                                        LocalDate.Format {
                                                            day()
                                                            char(' ')
                                                            monthName(MonthNames.ENGLISH_ABBREVIATED)
                                                        }
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    val categories by remember {
        mutableStateOf(
            (0..5).map {
                Category(
                    id = it.toLong(),
                    name = "Category $it",
                    colorArgb = Color.White.toArgb(),
                    categoryIcon = CategoryIcon.entries.random()
                )
            }
        )
    }
    val transactions by remember {
        mutableStateOf(
            (0..10).map {
                Transaction(
                    id = it.toLong(),
                    categoryId = categories.random().id,
                    amount = 10000.0,
                    date = Clock.System.now(),
                    note = "Transaction $it",
                    recurrence = Recurrence.entries.random()
                )
            }
        )
    }
    val incomes by remember {
        mutableStateOf(
            (0..2).map {
                Income(
                    id = it.toLong(),
                    amount = 50000.0,
                    title = "Income Source $it",
                    description = "Description $it",
                    recurrence = Recurrence.entries.random()
                )
            }
        )
    }

    AppTheme {
        HomePage(
            state = HomeState(
                name = "Shub39",
                currentCategory = categories.first(),
                transactions = transactions,
                incomes = incomes,
                totalIncome = incomes.sumOf { it.amount },
                outstandingBalance = incomes.sumOf { it.amount } - transactions.sumOf { it.amount },
                allCategories = categories
            ),
            onAction = { },
        )
    }
}