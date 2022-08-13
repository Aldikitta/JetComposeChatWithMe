package com.example.chatwithme.presentation.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BottomRouteSign(
    modifier: Modifier = Modifier,
    onclick: () -> Unit,
    signInOrSignUp: String,
    label: String
) {
    Surface(
        modifier = modifier
    )
    {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall
            )
            TextButton(
                onClick = onclick,
                content = {
                    Text(
                        text = signInOrSignUp,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            )
        }
    }
}