package com.example.chatwithme.presentation.auth.signUp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatwithme.core.SnackbarController
import com.example.chatwithme.presentation.auth.AuthViewModel
import com.example.chatwithme.presentation.bottomnavigation.BottomNavItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    emailFromSignIn: String,
    loginViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController
) {

    //Set SnackBar
    val toastMessage = loginViewModel.toastMessage.value
    LaunchedEffect(key1 = toastMessage){
        if(toastMessage != ""){
            SnackbarController(this).showSnackbar(snackbarHostState,toastMessage, "Close")
        }
    }

    //For test user information
    var textEmail: String? by remember { mutableStateOf("") }//gimli@gmail.com
    var textPassword: String? by remember { mutableStateOf("") }//123456
    LaunchedEffect(key1 = Unit){
        textEmail = emailFromSignIn
    }

    //Sign Up Navigate
    val isUserSignUp = loginViewModel.isUserSignUpState.value
    LaunchedEffect(key1 = isUserSignUp){
        if (isUserSignUp) {
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

                Text(text ="Sign up for ComApp",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 36.sp,
                    modifier = Modifier.padding(2.dp, 2.dp, 2.dp, 2.dp))

                Text(text ="A simple chat app.",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(2.dp, 2.dp, 2.dp, 30.dp))

//                Box(modifier = Modifier.padding(2.dp)){
//                    LoginEmailCustomOutlinedTextField(textEmail!!,"Email", Icons.Default.Email) {
//                        textEmail = it
//                    }
//                }
//                Box(modifier = Modifier.padding(2.dp)){
//                    LoginPasswordCustomOutlinedTextField(textPassword!!,"Password", Icons.Default.Password) {
//                        textPassword = it
//                    }
//                }

                Button(onClick = {
                    loginViewModel.signUp(textEmail!!, textPassword!!)
                },modifier = Modifier.padding(2.dp)) {
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }

        Surface(
            color = MaterialTheme.colorScheme.primary,
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
                Text(text = "Already have an account?", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                Text(text = " Log in", fontSize = 14.sp, color = Color.Red, modifier = Modifier.clickable {

                    if(textEmail == ""){
                        navController.popBackStack()
                        navController.navigate(BottomNavItem.SignIn.fullRoute)
                    }else{
                        navController.popBackStack()
                        navController.navigate(BottomNavItem.SignIn.screen_route + "?emailFromSignUp=$textEmail")
                    }
                })
            }
        }
    }
}