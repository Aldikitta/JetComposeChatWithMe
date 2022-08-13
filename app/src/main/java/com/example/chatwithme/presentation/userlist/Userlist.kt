package com.example.chatwithme.presentation.userlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavController
import com.example.chatwithme.domain.model.UserStatus
import com.example.chatwithme.presentation.commonComponents.LogOutCustomText

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Userlist(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController
) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

                Text(text = "ouu")
//            LogOutCustomText{
//                profileViewModel.setUserStatusToFirebaseAndSignOut(UserStatus.OFFLINE)
//            }

        }
    }

}