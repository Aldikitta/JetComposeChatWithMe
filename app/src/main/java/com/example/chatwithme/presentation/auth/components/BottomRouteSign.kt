package com.example.chatwithme.presentation.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.example.chatwithme.presentation.bottomnavigation.BottomNavItem

@Composable
fun BottomRouteSign(
    modifier: Modifier = Modifier,
    onclick: () -> Unit,
    signInOrSignUp: String
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
                text = "Don't have an account?",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = signInOrSignUp,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable {
                    onclick()
//                    if (textEmail == "") {
//                        navController.popBackStack()
//                        navController.navigate(BottomNavItem.SignUp.fullRoute)
//                    } else {
//                        navController.popBackStack()
//                        navController.navigate(BottomNavItem.SignUp.screen_route + "?emailFromSignIn=$textEmail")
//                    }
                })
        }
    }
}