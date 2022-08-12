package com.example.chatwithme.presentation.auth.signIn

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatwithme.presentation.auth.AuthViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    emailFromSignUp: String,
    loginViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController
) {
}