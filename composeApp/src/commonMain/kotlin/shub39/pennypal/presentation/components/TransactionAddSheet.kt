package shub39.pennypal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.vectorResource
import pennypal.composeapp.generated.resources.Res
import pennypal.composeapp.generated.resources.add
import pennypal.composeapp.generated.resources.edit
import shub39.pennypal.domain.Category
import shub39.pennypal.domain.CategoryIcon
import shub39.pennypal.domain.Recurrence
import shub39.pennypal.domain.Transaction
import shub39.pennypal.domain.TransactionType
import shub39.pennypal.domain.TransactionType.Companion.toDisplayString
import shub39.pennypal.presentation.endItemShape
import shub39.pennypal.presentation.leadingItemShape
import shub39.pennypal.presentation.middleItemShape
import shub39.pennypal.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TransactionAddSheet(
    transaction: Transaction,
    onAddTransaction: (Transaction) -> Unit,
    categories: List<Category>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var newTransaction by remember { mutableStateOf(transaction) }

    if (showDatePicker) {
        val datePickerState =
            rememberDatePickerState(
                initialSelectedDateMillis = newTransaction.date.toEpochMilliseconds()
            )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        newTransaction =
                            newTransaction.copy(
                                date =
                                    Instant.fromEpochMilliseconds(
                                        datePickerState.selectedDateMillis!!
                                    )
                            )
                        showDatePicker = false
                    },
                    modifier = Modifier,
                ) {
                    Text(text = "Confirm Date")
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .clip(MaterialTheme.shapes.medium),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    modifier =
                        Modifier.size(50.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = MaterialShapes.SoftBurst.toShape(),
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.add),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }

                Text(text = "Add New Transaction", style = MaterialTheme.typography.titleLarge)

                OutlinedTextField(
                    value = newTransaction.amount.toString(),
                    onValueChange = {
                        it.toDoubleOrNull()?.let { amt ->
                            newTransaction = newTransaction.copy(amount = amt)
                        }
                    },
                    label = { Text(text = "Add Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                OutlinedTextField(
                    value = newTransaction.note.toString(),
                    onValueChange = { newTransaction = newTransaction.copy(note = it) },
                    label = { Text(text = "Add Note") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                )

                Column {
                    Card(shape = leadingItemShape()) {
                        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                            Text(text = "Select Category")
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                categories.forEach { category ->
                                    ToggleButton(
                                        checked = category.id == newTransaction.categoryId,
                                        onCheckedChange = {
                                            newTransaction =
                                                newTransaction.copy(categoryId = category.id)
                                        },
                                    ) {
                                        Text(text = category.name)
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Card(shape = middleItemShape()) {
                        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                            Text(text = "Select Transaction Type")
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                TransactionType.entries.forEach { type ->
                                    ToggleButton(
                                        checked = type == newTransaction.transactionType,
                                        onCheckedChange = {
                                            newTransaction =
                                                newTransaction.copy(transactionType = type)
                                        },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(text = type.toDisplayString())
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Card(shape = endItemShape()) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Column {
                                Text(text = "Select Date")
                                Text(
                                    text =
                                        newTransaction.date
                                            .toLocalDateTime(TimeZone.UTC)
                                            .date
                                            .format(
                                                LocalDate.Format {
                                                    day()
                                                    char(' ')
                                                    monthName(MonthNames.ENGLISH_FULL)
                                                    char(' ')
                                                    year()
                                                }
                                            ),
                                    style = MaterialTheme.typography.labelMedium,
                                )
                            }
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    imageVector = vectorResource(Res.drawable.edit),
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        onAddTransaction(newTransaction)
                        onDismissRequest()
                    },
                    enabled = newTransaction.amount >= 0,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Add Transaction")
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
                    categoryIcon = CategoryIcon.entries.random(),
                )
            }
        )
    }

    AppTheme {
        TransactionAddSheet(
            transaction =
                Transaction(
                    id = 1L,
                    categoryId = 1L,
                    amount = 10000.0,
                    date = Clock.System.now(),
                    note = "A Transaction",
                    recurrence = Recurrence.WEEKLY,
                    transactionType = TransactionType.entries.random(),
                ),
            onAddTransaction = {},
            onDismissRequest = {},
            categories = categories,
        )
    }
}
