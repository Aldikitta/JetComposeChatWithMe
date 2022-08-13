package com.example.chatwithme.presentation.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chatwithme.ui.theme.spacing

@Composable
fun ButtonSign(
    onclick: () -> Unit,
    signInOrSignUp: String
) {
    Button(
    modifier = Modifier
    .padding(top = MaterialTheme.spacing.large)
    .fillMaxWidth(),
    onClick = {
        onclick()
//        authViewModel.signIn(textEmail!!, textPassword!!)
    })
    {
        Text(
            text = signInOrSignUp,
            style = MaterialTheme.typography.titleMedium
        )
    }
}