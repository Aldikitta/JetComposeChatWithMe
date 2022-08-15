package com.example.chatwithme.presentation.chat

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(
    chatRoomUUID: String,
    opponentUUID: String,
    registerUUID: String,
    oneSignalUserId : String,
//    chatViewModel: ChatViewModel = hiltViewModel(),
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController
) {
}