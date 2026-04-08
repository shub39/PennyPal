package shub39.pennypal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.vectorResource
import pennypal.composeapp.generated.resources.Res
import pennypal.composeapp.generated.resources.add
import shub39.pennypal.domain.Category
import shub39.pennypal.domain.CategoryIcon
import shub39.pennypal.domain.CategoryIcon.Companion.toDrawable
import shub39.pennypal.presentation.CategoryColors
import shub39.pennypal.presentation.endItemShape
import shub39.pennypal.presentation.leadingItemShape
import shub39.pennypal.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CategoryAddSheet(
    category: Category,
    onAddCategory: (Category) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var newCategory by remember { mutableStateOf(category) }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
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

            Text(text = "Add New Category", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = newCategory.name,
                onValueChange = { newCategory = newCategory.copy(name = it) },
                label = { Text(text = "Add Amount") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
            )

            Column {
                Card(shape = leadingItemShape()) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Text(text = "Select Color")
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            CategoryColors.forEach { color ->
                                Box(
                                    modifier =
                                        Modifier.size(50.dp)
                                            .background(color = color, shape = CircleShape)
                                            .border(
                                                width =
                                                    if (newCategory.colorArgb == color.toArgb())
                                                        2.dp
                                                    else 0.dp,
                                                color = MaterialTheme.colorScheme.primary,
                                                shape = CircleShape,
                                            )
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Card(shape = endItemShape()) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Text(text = "Select Icon")
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow {
                            CategoryIcon.entries.forEach { categoryIcon ->
                                IconToggleButton(
                                    checked = newCategory.categoryIcon == categoryIcon,
                                    onCheckedChange = {
                                        newCategory = newCategory.copy(categoryIcon = categoryIcon)
                                    },
                                    colors =
                                        IconToggleButtonColors(
                                            containerColor = MaterialTheme.colorScheme.secondary,
                                            contentColor = MaterialTheme.colorScheme.onSecondary,
                                            disabledContainerColor =
                                                MaterialTheme.colorScheme.tertiary,
                                            disabledContentColor =
                                                MaterialTheme.colorScheme.onTertiary,
                                            checkedContainerColor =
                                                MaterialTheme.colorScheme.primary,
                                            checkedContentColor =
                                                MaterialTheme.colorScheme.onPrimary,
                                        ),
                                ) {
                                    Icon(
                                        imageVector = vectorResource(categoryIcon.toDrawable()),
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    onAddCategory(newCategory)
                    onDismissRequest()
                },
                enabled = newCategory.name.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Add Category")
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    AppTheme {
        CategoryAddSheet(
            category =
                Category(
                    name = "New Category",
                    colorArgb = CategoryColors.first().toArgb(),
                    categoryIcon = CategoryIcon.SHOPPING,
                ),
            onAddCategory = {},
            onDismissRequest = {},
        )
    }
}
