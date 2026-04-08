package shub39.pennypal.presentation.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.TonalToggleButton
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.vectorResource
import pennypal.composeapp.generated.resources.Res
import pennypal.composeapp.generated.resources.add
import pennypal.composeapp.generated.resources.wallet
import shub39.pennypal.domain.Category
import shub39.pennypal.domain.CategoryIcon
import shub39.pennypal.domain.CategoryIcon.Companion.toDrawable
import shub39.pennypal.domain.Recurrence
import shub39.pennypal.domain.Recurrence.Companion.toDisplayString
import shub39.pennypal.domain.Transaction
import shub39.pennypal.domain.TransactionType
import shub39.pennypal.presentation.CategoryColors
import shub39.pennypal.presentation.components.CategoryAddSheet
import shub39.pennypal.presentation.components.TransactionAddSheet
import shub39.pennypal.presentation.detachedItemShape
import shub39.pennypal.presentation.endItemShape
import shub39.pennypal.presentation.leadingItemShape
import shub39.pennypal.presentation.middleItemShape
import shub39.pennypal.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomePage(state: HomeState, onAction: (HomeAction) -> Unit, modifier: Modifier = Modifier) {
    var fabExpanded by remember { mutableStateOf(false) }

    var showAddCategorySheet by remember { mutableStateOf(false) }
    var showAddTransactionSheet by remember { mutableStateOf(false) }

    if (showAddTransactionSheet && state.allCategories.isNotEmpty()) {
        TransactionAddSheet(
            transaction =
                Transaction(
                    categoryId = state.allCategories.first().id,
                    amount = 10.0,
                    date = Clock.System.now(),
                    note = null,
                    recurrence = Recurrence.NONE,
                    transactionType = TransactionType.EXPENSE,
                ),
            onAddTransaction = { onAction(HomeAction.AddTransaction(it)) },
            categories = state.allCategories,
            onDismissRequest = { showAddTransactionSheet = false },
        )
    }

    if (showAddCategorySheet) {
        CategoryAddSheet(
            category =
                Category(
                    name = "New Category",
                    colorArgb = CategoryColors.random().toArgb(),
                    categoryIcon = CategoryIcon.entries.random(),
                ),
            onAddCategory = { onAction(HomeAction.AddCategory(it)) },
            onDismissRequest = { showAddCategorySheet = false },
        )
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Box {
            Column(
                modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                // greeting
                Column {
                    Text(text = "Hey, ${state.name}", style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "Manage your expenses",
                        style =
                            MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                    )
                }

                // income card
                Box(
                    modifier =
                        Modifier.fillMaxWidth()
                            .background(
                                brush =
                                    Brush.linearGradient(
                                        0f to Color(0xffe8d1b2),
                                        0.3f to Color(0xffa0c6aa),
                                        0.7f to Color(0xff6fc0a6),
                                        1f to Color(0xff40b9a1),
                                    ),
                                shape = MaterialTheme.shapes.large,
                            )
                            .clip(MaterialTheme.shapes.large)
                ) {
                    Box(
                        modifier =
                            Modifier.matchParentSize()
                                .background(
                                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                                    shape = MaterialTheme.shapes.large,
                                )
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.wallet),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Your Wallet", style = MaterialTheme.typography.titleMedium)
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column {
                                Text(
                                    text = "Total Expenses",
                                    style =
                                        MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Normal
                                        ),
                                )
                                Text(
                                    text = "₹ ${state.totalExpenses.roundToInt()}",
                                    style =
                                        MaterialTheme.typography.headlineMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                )
                            }

                            Column {
                                Text(
                                    text = "Total Income",
                                    style =
                                        MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Normal
                                        ),
                                )
                                Text(
                                    text = "₹ ${state.totalIncome.roundToInt()}",
                                    style =
                                        MaterialTheme.typography.headlineMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                )
                            }
                        }
                    }
                }

                // your transactions
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column {
                        Text(text = "Your expenses", style = MaterialTheme.typography.titleMedium)

                        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            Recurrence.entries.forEachIndexed { index, recurrence ->
                                TonalToggleButton(
                                    checked = state.selectedRecurrence == recurrence,
                                    onCheckedChange = {
                                        onAction(HomeAction.SelectRecurrence(recurrence))
                                    },
                                    shapes =
                                        when (index) {
                                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                            Recurrence.entries.size - 1 ->
                                                ButtonGroupDefaults.connectedTrailingButtonShapes()

                                            else ->
                                                ButtonGroupDefaults.connectedMiddleButtonShapes()
                                        },
                                    modifier = Modifier.weight(1f),
                                ) {
                                    Text(text = recurrence.toDisplayString())
                                }
                            }
                        }
                    }

                    val transactionItems =
                        state.transactions.filter {
                            when (state.selectedRecurrence) {
                                Recurrence.NONE -> true
                                Recurrence.WEEKLY -> it.recurrence == Recurrence.WEEKLY
                                Recurrence.MONTHLY -> it.recurrence == Recurrence.MONTHLY
                            }
                        }

                    if (transactionItems.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(top = 80.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = "No transactions found",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                text = "Add a transaction to get started",
                                style = MaterialTheme.typography.bodyMedium,
                                color =
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            )
                        }
                    } else {
                        val itemsGroupedByCategory =
                            transactionItems.groupBy { item ->
                                state.allCategories.find { it.id == item.categoryId }!!
                            }
                        LazyColumn(
                            modifier =
                                Modifier.fillMaxWidth()
                                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                            contentPadding = PaddingValues(top = 8.dp, bottom = 60.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                        ) {
                            itemsGroupedByCategory.forEach { group ->
                                item {
                                    Box(
                                        modifier =
                                            Modifier.fillMaxWidth()
                                                .background(
                                                    brush =
                                                        Brush.horizontalGradient(
                                                            0f to
                                                                MaterialTheme.colorScheme
                                                                    .surfaceContainerHighest,
                                                            0.7f to
                                                                Color(group.key.colorArgb)
                                                                    .copy(alpha = 0.5f),
                                                            1f to Color(group.key.colorArgb),
                                                        ),
                                                    shape = RoundedCornerShape(16.dp),
                                                )
                                                .clip(RoundedCornerShape(16.dp))
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            modifier = Modifier.padding(8.dp),
                                        ) {
                                            Icon(
                                                imageVector =
                                                    vectorResource(
                                                        group.key.categoryIcon.toDrawable()
                                                    ),
                                                contentDescription = null,
                                            )
                                            Text(
                                                text = group.key.name,
                                                style = MaterialTheme.typography.titleSmall,
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        group.value.forEachIndexed { index, item ->
                                            val shape =
                                                when {
                                                    group.value.size == 1 -> detachedItemShape()
                                                    index == 0 -> leadingItemShape()
                                                    index == group.value.size - 1 -> endItemShape()
                                                    else -> middleItemShape()
                                                }

                                            Card(
                                                modifier = Modifier,
                                                onClick = {
                                                    onAction(HomeAction.DeleteTransaction(item.id))
                                                },
                                                shape = shape,
                                                colors =
                                                    when (item.transactionType) {
                                                        TransactionType.INCOME ->
                                                            CardDefaults.cardColors(
                                                                containerColor = Color.Transparent,
                                                                contentColor =
                                                                    MaterialTheme.colorScheme
                                                                        .onPrimaryContainer,
                                                            )

                                                        TransactionType.EXPENSE ->
                                                            CardDefaults.cardColors(
                                                                containerColor = Color.Transparent,
                                                                contentColor =
                                                                    MaterialTheme.colorScheme
                                                                        .onSecondaryContainer,
                                                            )
                                                    },
                                            ) {
                                                Row(
                                                    modifier =
                                                        Modifier.background(
                                                                brush =
                                                                    when (item.transactionType) {
                                                                        TransactionType.INCOME ->
                                                                            Brush.linearGradient(
                                                                                0f to
                                                                                    MaterialTheme
                                                                                        .colorScheme
                                                                                        .primaryContainer,
                                                                                0.7f to
                                                                                    Color.Green
                                                                                        .copy(
                                                                                            alpha =
                                                                                                0.3f
                                                                                        ),
                                                                                0.95f to
                                                                                    Color.Green
                                                                                        .copy(
                                                                                            alpha =
                                                                                                0.5f
                                                                                        ),
                                                                                1f to
                                                                                    Color.Green
                                                                                        .copy(
                                                                                            alpha =
                                                                                                0.6f
                                                                                        ),
                                                                            )

                                                                        TransactionType.EXPENSE ->
                                                                            Brush.linearGradient(
                                                                                0f to
                                                                                    MaterialTheme
                                                                                        .colorScheme
                                                                                        .secondaryContainer,
                                                                                0.7f to
                                                                                    Color.Red.copy(
                                                                                        alpha = 0.3f
                                                                                    ),
                                                                                0.95f to
                                                                                    Color.Red.copy(
                                                                                        alpha = 0.5f
                                                                                    ),
                                                                                1f to
                                                                                    Color.Red.copy(
                                                                                        alpha = 0.6f
                                                                                    ),
                                                                            )
                                                                    }
                                                            )
                                                            .fillMaxWidth()
                                                            .padding(16.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                ) {
                                                    Column {
                                                        Text(
                                                            text =
                                                                "${if (item.transactionType == TransactionType.INCOME) "+" else "-"}₹ ${item.amount.roundToInt()}",
                                                            style =
                                                                MaterialTheme.typography.bodyLarge,
                                                        )
                                                        item.note?.let {
                                                            Text(
                                                                text = it,
                                                                style =
                                                                    MaterialTheme.typography
                                                                        .labelMedium,
                                                            )
                                                        }
                                                    }

                                                    Text(
                                                        text =
                                                            item.date
                                                                .toLocalDateTime(TimeZone.UTC)
                                                                .date
                                                                .format(
                                                                    LocalDate.Format {
                                                                        day()
                                                                        char(' ')
                                                                        monthName(
                                                                            MonthNames
                                                                                .ENGLISH_ABBREVIATED
                                                                        )
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

            FloatingActionButtonMenu(
                expanded = fabExpanded,
                button = {
                    ToggleFloatingActionButton(
                        checked = fabExpanded,
                        onCheckedChange = { fabExpanded = !fabExpanded },
                    ) {
                        val iconColor by
                            animateColorAsState(
                                targetValue =
                                    if (checkedProgress >= 0.6f) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.primary
                            )

                        Icon(
                            imageVector = vectorResource(Res.drawable.add),
                            contentDescription = null,
                            tint = iconColor,
                        )
                    }
                },
                modifier = Modifier.align(Alignment.BottomEnd).padding(0.dp),
            ) {
                FloatingActionButtonMenuItem(
                    onClick = {
                        showAddCategorySheet = true
                        fabExpanded = false
                    },
                    text = { Text(text = "Add Category") },
                    icon = {},
                )
                FloatingActionButtonMenuItem(
                    onClick = {
                        showAddTransactionSheet = true
                        fabExpanded = false
                    },
                    text = { Text(text = "Add Transaction") },
                    icon = {},
                )
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
                    categoryIcon = CategoryIcon.entries.random(),
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
                    recurrence = Recurrence.entries.random(),
                    transactionType = TransactionType.entries.random(),
                )
            }
        )
    }

    AppTheme {
        HomePage(
            state =
                HomeState(
                    name = "Shub39",
                    transactions = transactions,
                    totalExpenses =
                        transactions
                            .filter { it.transactionType == TransactionType.EXPENSE }
                            .sumOf { it.amount },
                    totalIncome =
                        transactions
                            .filter { it.transactionType == TransactionType.INCOME }
                            .sumOf { it.amount },
                    allCategories = categories,
                ),
            onAction = {},
        )
    }
}
