package shub39.pennypal.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import org.jetbrains.compose.resources.vectorResource
import pennypal.composeapp.generated.resources.Res
import pennypal.composeapp.generated.resources.edit
import pennypal.composeapp.generated.resources.wallet
import shub39.pennypal.domain.Category
import shub39.pennypal.domain.CategoryIcon
import shub39.pennypal.domain.Income
import shub39.pennypal.domain.Transaction
import shub39.pennypal.presentation.theme.AppTheme
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.time.Clock

@Composable
fun HomePage(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                                style =  MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal)
                            )
                            Text(
                                text = "₹ ${state.totalIncome.roundToInt()}",
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                            )
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
            (0..3).map { 
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
            (0..3).map {
                Transaction(
                    id = it.toLong(),
                    categoryId = categories.random().id,
                    amount = 10000.0,
                    date = Clock.System.now(),
                    note = "Transaction $it"
                )
            }
        )
    }
    val incomes by remember {
        mutableStateOf(
            (0..2).map {
                Income(
                    id = it.toLong(),
                    amount = 20000.0,
                    monthlyRecurring = Random.nextBoolean(),
                    title = "Income Source $it",
                    description = "Description $it"
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
            onAction = {  },
        )
    }
}