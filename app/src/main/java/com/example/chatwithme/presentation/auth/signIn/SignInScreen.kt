package com.example.chatwithme.presentation.auth.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatwithme.R
import com.example.chatwithme.core.SnackbarController
import com.example.chatwithme.presentation.auth.AuthViewModel
import com.example.chatwithme.presentation.auth.components.*
import com.example.chatwithme.presentation.bottomnavigation.BottomNavItem
import com.example.chatwithme.ui.theme.spacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    emailFromSignUp: String,
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController
) {
    //Set SnackBar
    val snackbar = authViewModel.toastMessage.value
    LaunchedEffect(key1 = snackbar) {
        if (snackbar != "") {
            SnackbarController(this).showSnackbar(snackbarHostState, snackbar, "Close")
        }
    }

    //For test user information
    var textEmail: String? by remember { mutableStateOf("") }//gimli@gmail.com
    var textPassword: String? by remember { mutableStateOf("") }//123456

    LaunchedEffect(key1 = Unit) {
        textEmail = emailFromSignUp
    }

    //Check User Authenticated
    val isUserAuthenticated = authViewModel.isUserAuthenticatedState.value
    LaunchedEffect(Unit) {
        if (isUserAuthenticated) {
            navController.navigate(BottomNavItem.UserList.fullRoute)
        }
    }

    //Sign In Navigate
    val isUserSignIn = authViewModel.isUserSignInState.value
    LaunchedEffect(key1 = isUserSignIn) {
        if (isUserSignIn) {
            keyboardController.hide()
            navController.navigate(BottomNavItem.Profile.fullRoute)
        }
    }
    Column {
        Surface(
            modifier = Modifier
                .weight(8f)
                .fillMaxSize()
                .focusable(true)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController.hide()
                    })
                }
                .padding(MaterialTheme.spacing.large)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_chatwithme),
                    contentDescription = null
                )
                TextLightweight()

                Box(modifier = Modifier.padding(top = MaterialTheme.spacing.extraLarge)) {
                    LoginEmailCustomOutlinedTextField(textEmail!!, "Email", Icons.Default.Email) {
                        textEmail = it
                    }
                }

                Box(modifier = Modifier.padding(top = MaterialTheme.spacing.medium)) {
                    LoginPasswordCustomOutlinedTextField(
                        textPassword!!,
                        "Password",
                        Icons.Default.Password
                    ) {
                        textPassword = it
                    }
                }

                ButtonSign(
                    onclick = {
                        authViewModel.signIn(textEmail!!, textPassword!!)
                    },
                    signInOrSignUp = "Sign In"
                )
            }
        }
        BottomRouteSign(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .focusable(true)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { keyboardController.hide() })
                },
            onclick = {
                if (textEmail == "") {
                    navController.popBackStack()
                    navController.navigate(BottomNavItem.SignUp.fullRoute)
                } else {
                    navController.popBackStack()
                    navController.navigate(BottomNavItem.SignUp.screen_route + "?emailFromSignIn=$textEmail")
                }
            },
            signInOrSignUp = "Sign Up",
            label = "Don't have an account?"
        )
    }
}