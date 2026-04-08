package shub39.pennypal.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.lerp
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
import shub39.pennypal.presentation.*
import shub39.pennypal.presentation.components.CategoryAddSheet
import shub39.pennypal.presentation.components.TransactionAddSheet
import shub39.pennypal.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    userName: String,
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var fabExpanded by remember { mutableStateOf(false) }
    var showAddCategorySheet by remember { mutableStateOf(false) }
    var showAddTransactionSheet by remember { mutableStateOf(false) }

    // Sheets
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
                GreetingSection(name = userName)

                WalletCard(totalExpenses = state.totalExpenses, totalIncome = state.totalIncome)

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    RecurrenceSelector(
                        selectedRecurrence = state.selectedRecurrence,
                        onRecurrenceSelected = { onAction(HomeAction.SelectRecurrence(it)) },
                    )

                    val transactionItems =
                        remember(state.transactions, state.selectedRecurrence) {
                            state.transactions.filter {
                                when (state.selectedRecurrence) {
                                    Recurrence.NONE -> true
                                    Recurrence.WEEKLY -> it.recurrence == Recurrence.WEEKLY
                                    Recurrence.MONTHLY -> it.recurrence == Recurrence.MONTHLY
                                }
                            }
                        }

                    AnimatedContent(targetState = transactionItems) { observedItems ->
                        TransactionList(
                            transactions = observedItems,
                            allCategories = state.allCategories,
                            onDeleteTransaction = { onAction(HomeAction.DeleteTransaction(it)) },
                        )
                    }
                }
            }

            HomeFabMenu(
                expanded = fabExpanded,
                onExpandedChange = { fabExpanded = it },
                onAddCategory = {
                    showAddCategorySheet = true
                    fabExpanded = false
                },
                onAddTransaction = {
                    showAddTransactionSheet = true
                    fabExpanded = false
                },
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            )
        }
    }
}

@Composable
private fun GreetingSection(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Hey, $name", style = MaterialTheme.typography.titleLarge)
        Text(
            text = "Manage your expenses",
            style =
                MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
        )
    }
}

@Composable
private fun WalletCard(totalExpenses: Double, totalIncome: Double, modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
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
                WalletStat(label = "Total Expenses", amount = totalExpenses)
                WalletStat(label = "Total Income", amount = totalIncome)
            }
        }
    }
}

@Composable
private fun WalletStat(label: String, amount: Double) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
        )
        Text(
            text = "₹ ${amount.roundToInt()}",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun RecurrenceSelector(
    selectedRecurrence: Recurrence,
    onRecurrenceSelected: (Recurrence) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Your expenses", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            Recurrence.entries.forEachIndexed { index, recurrence ->
                TonalToggleButton(
                    checked = selectedRecurrence == recurrence,
                    onCheckedChange = { onRecurrenceSelected(recurrence) },
                    shapes =
                        when (index) {
                            0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                            Recurrence.entries.size - 1 ->
                                ButtonGroupDefaults.connectedTrailingButtonShapes()
                            else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                        },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(text = recurrence.toDisplayString())
                }
            }
        }
    }
}

@Composable
private fun TransactionList(
    transactions: List<Transaction>,
    allCategories: List<Category>,
    onDeleteTransaction: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (transactions.isEmpty()) {
        Column(
            modifier = modifier.fillMaxWidth().padding(top = 80.dp),
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
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            )
        }
    } else {
        val itemsGroupedByCategory =
            transactions.groupBy { item -> allCategories.find { it.id == item.categoryId }!! }
        LazyColumn(
            modifier =
                modifier.fillMaxWidth().clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            contentPadding = PaddingValues(top = 8.dp, bottom = 60.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            itemsGroupedByCategory.forEach { (category, categoryTransactions) ->
                item {
                    CategoryGroup(
                        category = category,
                        transactions = categoryTransactions,
                        onDeleteTransaction = onDeleteTransaction,
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryGroup(
    category: Category,
    transactions: List<Transaction>,
    onDeleteTransaction: (Long) -> Unit,
) {
    Column {
        Box(
            modifier =
                Modifier.fillMaxWidth()
                    .background(
                        brush =
                            Brush.horizontalGradient(
                                0f to MaterialTheme.colorScheme.surfaceContainerHighest,
                                0.7f to Color(category.colorArgb).copy(alpha = 0.5f),
                                1f to Color(category.colorArgb),
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
                    imageVector = vectorResource(category.categoryIcon.toDrawable()),
                    contentDescription = null,
                )
                Text(text = category.name, style = MaterialTheme.typography.titleSmall)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            transactions.forEachIndexed { index, item ->
                val shape =
                    when {
                        transactions.size == 1 -> detachedItemShape()
                        index == 0 -> leadingItemShape()
                        index == transactions.size - 1 -> endItemShape()
                        else -> middleItemShape()
                    }
                TransactionItem(
                    transaction = item,
                    shape = shape,
                    onClick = { onDeleteTransaction(item.id) },
                )
            }
        }
    }
}

@Composable
private fun TransactionItem(transaction: Transaction, shape: Shape, onClick: () -> Unit) {
    Card(
        modifier = Modifier,
        onClick = onClick,
        shape = shape,
        colors =
            CardDefaults.cardColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
    ) {
        Row(
            modifier =
                Modifier.background(
                        brush =
                            when (transaction.transactionType) {
                                TransactionType.INCOME ->
                                    Brush.horizontalGradient(
                                        0f to MaterialTheme.colorScheme.surfaceContainerHighest,
                                        0.7f to MaterialTheme.colorScheme.surfaceContainerHighest,
                                        0.95f to Color(0xFF3A782B),
                                        1f to Color(0xFF3A782B),
                                    )

                                TransactionType.EXPENSE ->
                                    Brush.linearGradient(
                                        0f to MaterialTheme.colorScheme.surfaceContainerHighest,
                                        0.7f to MaterialTheme.colorScheme.surfaceContainerHighest,
                                        0.95f to Color(0xFF9E1C25),
                                        1f to Color(0xFF9E1C25),
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
                        "${if (transaction.transactionType == TransactionType.INCOME) "+" else "-"}₹ ${transaction.amount.roundToInt()}",
                    style = MaterialTheme.typography.bodyLarge,
                )
                transaction.note?.let {
                    Text(text = it, style = MaterialTheme.typography.labelMedium)
                }
            }

            Text(
                text =
                    transaction.date
                        .toLocalDateTime(TimeZone.UTC)
                        .date
                        .format(
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun HomeFabMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onAddCategory: () -> Unit,
    onAddTransaction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color1 = MaterialTheme.colorScheme.secondaryContainer
    val color2 = MaterialTheme.colorScheme.primary
    FloatingActionButtonMenu(
        expanded = expanded,
        button = {
            ToggleFloatingActionButton(
                checked = expanded,
                onCheckedChange = onExpandedChange,
                containerColor = { progress -> lerp(color1, color2, progress) },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.add),
                    contentDescription = null,
                    tint =
                        lerp(
                            MaterialTheme.colorScheme.onSecondaryContainer,
                            MaterialTheme.colorScheme.onPrimary,
                            checkedProgress,
                        ),
                )
            }
        },
        modifier = modifier,
    ) {
        FloatingActionButtonMenuItem(
            onClick = onAddCategory,
            text = { Text(text = "Add Category") },
            icon = {},
        )
        FloatingActionButtonMenuItem(
            onClick = onAddTransaction,
            text = { Text(text = "Add Transaction") },
            icon = {},
        )
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
            userName = "Shub39",
            state =
                HomeState(
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
