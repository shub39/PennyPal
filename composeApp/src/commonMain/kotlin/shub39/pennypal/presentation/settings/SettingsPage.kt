package shub39.pennypal.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.vectorResource
import pennypal.composeapp.generated.resources.Res
import pennypal.composeapp.generated.resources.dark_mode
import pennypal.composeapp.generated.resources.delete
import pennypal.composeapp.generated.resources.light_mode
import shub39.pennypal.domain.AppTheme
import shub39.pennypal.domain.AppTheme.Companion.toDisplayString
import shub39.pennypal.presentation.endItemShape
import shub39.pennypal.presentation.leadingItemShape
import shub39.pennypal.presentation.listItemColors
import shub39.pennypal.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsPage(
    userName: String,
    onChangeUserName: (String) -> Unit,
    appTheme: AppTheme,
    onChangeAppTheme: (AppTheme) -> Unit,
    onDeleteData: () -> Unit,
    isDataEmpty: Boolean,
    modifier: Modifier = Modifier,
) {
    var newUserName by remember { mutableStateOf(userName) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Column { Text(text = "Settings", style = MaterialTheme.typography.titleLarge) }

            Column {
                OutlinedTextField(
                    value = newUserName,
                    onValueChange = { newUserName = it },
                    label = { Text(text = "Edit user name") },
                    placeholder = { Text(text = "Ex. John Doe") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions =
                        KeyboardActions(
                            onDone = {
                                if (newUserName.isNotBlank() && newUserName != userName) {
                                    onChangeUserName(newUserName)
                                    keyboardController?.hide()
                                }
                            }
                        ),
                )
                Button(
                    onClick = {
                        onChangeUserName(newUserName)
                        keyboardController?.hide()
                    },
                    enabled = newUserName.isNotBlank() && newUserName != userName,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Save")
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Column(modifier = Modifier.clip(leadingItemShape())) {
                    ListItem(
                        leadingContent = {
                            Icon(
                                imageVector =
                                    vectorResource(
                                        when (appTheme) {
                                            AppTheme.SYSTEM -> {
                                                if (isSystemInDarkTheme()) Res.drawable.dark_mode
                                                else Res.drawable.light_mode
                                            }

                                            AppTheme.DARK -> Res.drawable.dark_mode
                                            AppTheme.LIGHT -> Res.drawable.light_mode
                                        }
                                    ),
                                contentDescription = null,
                            )
                        },
                        headlineContent = { Text(text = "AppTheme") },
                        colors = listItemColors(),
                    )
                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                        modifier =
                            Modifier.fillMaxWidth()
                                .background(listItemColors().containerColor)
                                .padding(start = 52.dp, end = 16.dp, bottom = 8.dp),
                    ) {
                        AppTheme.entries.forEach { theme ->
                            ToggleButton(
                                checked = theme == appTheme,
                                onCheckedChange = { onChangeAppTheme(theme) },
                                modifier = Modifier.weight(1f),
                                colors =
                                    ToggleButtonDefaults.toggleButtonColors(
                                        containerColor =
                                            MaterialTheme.colorScheme.surfaceContainerLow
                                    ),
                            ) {
                                Text(text = theme.toDisplayString())
                            }
                        }
                    }
                }

                Column(modifier = Modifier.clip(endItemShape())) {
                    ListItem(
                        leadingContent = {
                            Icon(
                                imageVector = vectorResource(Res.drawable.delete),
                                contentDescription = null,
                            )
                        },
                        headlineContent = { Text(text = "Delete all data") },
                        supportingContent = {
                            Text(
                                text =
                                    "Delete all data saved in the database and start from scratch",
                                maxLines = 1,
                                modifier = Modifier.basicMarquee(),
                            )
                        },
                        colors = listItemColors(),
                    )
                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                        modifier =
                            Modifier.fillMaxWidth()
                                .background(listItemColors().containerColor)
                                .padding(start = 52.dp, end = 16.dp, bottom = 8.dp),
                    ) {
                        Button(
                            onClick = onDeleteData,
                            enabled = !isDataEmpty,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(text = "Delete")
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
    AppTheme {
        SettingsPage(
            appTheme = AppTheme.DARK,
            onChangeAppTheme = {},
            onDeleteData = {},
            isDataEmpty = false,
            userName = "User",
            onChangeUserName = {},
        )
    }
}
