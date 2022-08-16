package com.example.chatwithme.presentation.bottomnavigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.chatwithme.presentation.bottomnavigation.BottomNavItem

@Composable
fun CustomNavItem(
    onClick: () -> Unit,
    iconSelected: @Composable () -> Unit
) {
    IconButton(onClick = {
        onClick()
//        navController.navigate(BottomNavItem.Profile.screen_route) {
//            navController.graph.startDestinationRoute?.let { screen_route ->
//                popUpTo(screen_route) {
//                    saveState = true
//                }
//            }
//            launchSingleTop = true
//            restoreState = true
//        }
    },
        content = { iconSelected() }
    )
//    {
//        if (currentRoute == BottomNavItem.Profile.screen_route) {
//            Icon(
//                imageVector = Icons.Filled.Person,
//                contentDescription = null,
//                tint = MaterialTheme.colorScheme.primary
//            )
//        } else {
//            Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
//        }
//
//    }
}