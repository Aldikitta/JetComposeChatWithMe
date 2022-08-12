package com.example.chatwithme.presentation.auth.signUp

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatwithme.presentation.auth.AuthViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    emailFromSignIn: String,
    loginViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController
) {
    Text(text = "hola")
}