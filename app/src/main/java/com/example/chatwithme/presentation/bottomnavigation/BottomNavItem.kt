package com.example.chatwithme.presentation.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    var title: String,
    var icon: ImageVector,
    var screen_route: String,
    var arguments: String
) {
    object SignIn : BottomNavItem(
        "SignIn",
        Icons.Filled.Person,
        "signin",
        "?emailFromSignUp={emailFromSignUp}"
    ) {
        val fullRoute = screen_route + arguments
    }

    object SignUp : BottomNavItem(
        "SignUp",
        Icons.Filled.Person,
        "signup",
        "?emailFromSignIn={emailFromSignIn}"
    ) {
        val fullRoute = screen_route + arguments
    }

    object Profile : BottomNavItem(
        "Profile",
        Icons.Filled.Person,
        "profile",
        ""
    ) {
        val fullRoute = screen_route + arguments
    }

    object UserList : BottomNavItem(
        "Chat",
        Icons.Filled.Chat,
        "userlist",
        ""
    ) {
        val fullRoute = screen_route + arguments
    }

    object Chat : BottomNavItem(
        "Chat",
        Icons.Filled.Chat,
        "chat",
        "/{chatroomUUID}" + "/{opponentUUID}" + "/{registerUUID}" + "/{oneSignalUserId}"
    ) {
        val fullRoute = screen_route + arguments
    }

}