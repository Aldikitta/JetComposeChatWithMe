package com.example.chatwithme.presentation.bottomnavigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Chair
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.chatwithme.domain.model.UserStatus
import com.example.chatwithme.presentation.profile.ProfileViewModel
import com.example.chatwithme.ui.theme.spacing

@Composable
fun BottomNavigation(
    navController: NavController,
    bottomBarState: Boolean,
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()

    val items = listOf(
        BottomNavItem.Profile,
        BottomNavItem.UserList
    )
    val isUserSignOut = profileViewModel.isUserSignOutState.value
    LaunchedEffect(key1 = isUserSignOut){
        if(isUserSignOut){
            navController.popBackStack()
            navController.navigate(BottomNavItem.SignIn.fullRoute)
        }
    }
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomAppBar(
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            IconButton(onClick = {
                navController.navigate(BottomNavItem.Profile.screen_route) {
                    navController.graph.startDestinationRoute?.let { screen_route ->
                        popUpTo(screen_route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                if (currentRoute == BottomNavItem.Profile.screen_route) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
                }

            }
            IconButton(onClick = {
                navController.navigate(BottomNavItem.UserList.screen_route) {
                    navController.graph.startDestinationRoute?.let { screen_route ->
                        popUpTo(screen_route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                if (currentRoute == BottomNavItem.UserList.screen_route) {
                    Icon(
                        imageVector = Icons.Filled.Chat,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(imageVector = Icons.Outlined.Chat, contentDescription = null)
                }
            }
            Spacer(Modifier.weight(1f, true))
            if (currentRoute == BottomNavItem.UserList.screen_route){
                ExtendedFloatingActionButton(
                    modifier = Modifier.padding(end = MaterialTheme.spacing.small),
                    onClick = {

                    },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Text(text = "Add New Message")
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }else{
                ExtendedFloatingActionButton(
                    modifier = Modifier.padding(end = MaterialTheme.spacing.small),
                    onClick = {
                        profileViewModel.setUserStatusToFirebaseAndSignOut(UserStatus.OFFLINE)
                    },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Text(text = "Logging Out")
                    Icon(imageVector = Icons.Filled.CallMissedOutgoing, contentDescription = null)
                }
            }

//            items.forEach { item ->
//                NavigationBarItem(
//                    icon = {
//                        Icon(imageVector = item.icon, contentDescription = item.title)
//                    },
//                    label = {
//                        Text(item.title)
//                    },
//                    selected = currentRoute == item.screen_route,
//                    onClick = {
//                        navController.navigate(item.screen_route) {
//                            navController.graph.startDestinationRoute?.let { screen_route ->
//                                popUpTo(screen_route) {
//                                    saveState = true
//                                }
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                )
//            }
        }
    }
}