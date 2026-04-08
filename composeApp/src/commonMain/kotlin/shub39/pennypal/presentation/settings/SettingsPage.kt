package shub39.pennypal.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import shub39.pennypal.presentation.theme.AppTheme

@Composable
fun AuthPage(state: SettingsState, onAction: (SettingsAction) -> Unit, modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            Column {
                // header
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Box(
                        modifier =
                            Modifier.size(50.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(16.dp),
                                ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "P",
                            style =
                                MaterialTheme.typography.headlineLarge.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Welcome to PennyPal",
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = "Manage and Track your Expenses Seamlessly",
                            style =
                                MaterialTheme.typography.bodyMedium.copy(
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                        )
                    }
                }

                // Setup
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Column {
                            Text(text = "Get Started", style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = "Enter your name to continue",
                                style =
                                    MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    ),
                            )
                        }

                        OutlinedTextField(
                            value = state.name,
                            onValueChange = { onAction(SettingsAction.OnNameChange(it)) },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = "What should we call you?") },
                            placeholder = { Text(text = "Ex: John Doe") },
                            shape = MaterialTheme.shapes.medium,
                        )

                        Button(
                            onClick = {},
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(text = "Continue")
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
    AppTheme { AuthPage(state = SettingsState(), onAction = {}) }
}
