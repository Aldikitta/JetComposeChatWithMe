package com.example.chatwithme.presentation.commonComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ChatSnackBar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
) {
    val actionLabel = snackbarData.visuals.actionLabel
    val actionComposable: (@Composable () -> Unit)? = if (actionLabel != null) {
        @Composable {
            TextButton(
                onClick = { snackbarData.performAction() },
                content = { Text(actionLabel, color = MaterialTheme.colorScheme.error) }
            )
        }
    } else {
        null
    }
    Snackbar(
        content = { Text(snackbarData.visuals.message) },
        action = actionComposable,
        actionOnNewLine = actionOnNewLine,
    )
}