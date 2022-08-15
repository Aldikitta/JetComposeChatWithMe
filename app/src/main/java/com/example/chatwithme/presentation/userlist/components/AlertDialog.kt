package com.example.chatwithme.presentation.userlist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*

@Composable
fun AlertDialogChat(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val dialogText = "Add user via email".trimIndent()
    var emailInput by remember {
        mutableStateOf("")
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(emailInput)
                }
            ) {
                Text(text = "OK")
            }
        },
        title = {
            Text(text = "Add User")
        },
        text = {
            Column() {
                Text(text = dialogText)
                AlertDialogCustomOutlinedTextField(
                    entry = emailInput,
                    hint = "email",
                    onChange = { emailInput = it })
            }
        }
    )
}