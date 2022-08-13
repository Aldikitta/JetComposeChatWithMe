package com.example.chatwithme.presentation.auth.signIn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatwithme.core.SnackbarController
import com.example.chatwithme.presentation.auth.AuthViewModel
import com.example.chatwithme.presentation.auth.components.LoginEmailCustomOutlinedTextField
import com.example.chatwithme.presentation.auth.components.LoginPasswordCustomOutlinedTextField
import com.example.chatwithme.presentation.bottomnavigation.BottomNavItem

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
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
    var textEmail: String? by remember {mutableStateOf("")}//gimli@gmail.com
    var textPassword: String? by remember { mutableStateOf("") }//123456

    LaunchedEffect(key1 = Unit) {
        textEmail = emailFromSignUp
    }

    //Check User Authenticated
    val isUserAuthenticated = authViewModel.isUserAuthenticatedState.value
    LaunchedEffect(Unit) {
        if (isUserAuthenticated) {
            navController.navigate(BottomNavItem.Profile.fullRoute)
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
    //Compose Components
    Column() {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .weight(8f)
                .fillMaxSize()
                .focusable(true)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController.hide()
                    })
                }
                .padding(20.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 120.dp)) {

                Icon(
                    modifier = Modifier
                        .size(120.dp),
                    imageVector = Icons.Default.Chat,
                    contentDescription = "Logo Icon",
                    tint = MaterialTheme.colorScheme.onBackground
                )

                Text(text ="Log in to ComApp",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 36.sp,
                    modifier = Modifier.padding(2.dp, 2.dp, 2.dp, 2.dp))

                Text(text ="A simple chat app.",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(2.dp, 2.dp, 2.dp, 30.dp))

                Box(modifier = Modifier.padding(2.dp)){
                    LoginEmailCustomOutlinedTextField(textEmail!!,"Email", Icons.Default.Email) {
                        textEmail = it
                    }
                }

                Box(modifier = Modifier.padding(2.dp)){
                    LoginPasswordCustomOutlinedTextField(textPassword!!,"Password", Icons.Default.Password) {
                        textPassword = it
                    }
                }

                Button(onClick = {
                    authViewModel.signIn(textEmail!!,textPassword!!)
                },modifier = Modifier.padding(2.dp)) {
                    Text(
                        text = "Log In",
                        color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }

        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .focusable(true)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { keyboardController.hide() })
                }) {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 30.dp)) {
                Text(text = "Don't have an account?", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                Text(text = " Sign up", fontSize = 14.sp, color = Color.Red, modifier = Modifier.clickable {

                    if(textEmail == ""){
                        navController.popBackStack()
                        navController.navigate(BottomNavItem.SignUp.fullRoute)
                    }else{
                        navController.popBackStack()
                        navController.navigate(BottomNavItem.SignUp.screen_route + "?emailFromSignIn=$textEmail")
                    }
                })
            }
        }
    }
}