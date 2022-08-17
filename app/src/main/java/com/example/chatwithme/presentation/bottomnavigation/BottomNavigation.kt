package com.example.chatwithme.presentation.bottomnavigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.chatwithme.core.SnackbarController
import com.example.chatwithme.presentation.bottomnavigation.components.CustomNavItem
import com.example.chatwithme.presentation.userlist.UserListViewModel
import com.example.chatwithme.presentation.userlist.components.AlertDialogChat
import com.example.chatwithme.ui.theme.spacing

@Composable
fun BottomNavigation(
    navController: NavController,
    bottomBarState: Boolean,
    snackbarHostState: SnackbarHostState,
) {
    val items = listOf(
        BottomNavItem.Profile,
        BottomNavItem.UserList
    )
    val userListViewModel: UserListViewModel = hiltViewModel()

    val toastMessage = userListViewModel.toastMessage.value
    LaunchedEffect(key1 = toastMessage) {
        if (toastMessage != "") {
            SnackbarController(this).showSnackbar(snackbarHostState, toastMessage, "Close")
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
//            IconButton(onClick = {
//                navController.navigate(BottomNavItem.Profile.screen_route) {
//                    navController.graph.startDestinationRoute?.let { screen_route ->
//                        popUpTo(screen_route) {
//                            saveState = true
//                        }
//                    }
//                    launchSingleTop = true
//                    restoreState = true
//                }
//            }) {
//                if (currentRoute == BottomNavItem.Profile.screen_route) {
//                    Icon(
//                        imageVector = Icons.Filled.Person,
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.primary
//                    )
//                } else {
//                    Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
//                }
//
//            }
//            IconButton(onClick = {
//                navController.navigate(BottomNavItem.UserList.screen_route) {
//                    navController.graph.startDestinationRoute?.let { screen_route ->
//                        popUpTo(screen_route) {
//                            saveState = true
//                        }
//                    }
//                    launchSingleTop = true
//                    restoreState = true
//                }
//            }) {
//                if (currentRoute == BottomNavItem.UserList.screen_route) {
//                    Icon(
//                        imageVector = Icons.Filled.Chat,
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.primary
//                    )
//                } else {
//                    Icon(imageVector = Icons.Outlined.Chat, contentDescription = null)
//                }
//            }
            items.forEach { item ->
                CustomNavItem(
                    onClick = {
                        navController.navigate(item.screen_route) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    iconSelected = {
                        if (currentRoute == item.screen_route){
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }else{
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                            )
                        }
                    }
                )
            }
            Spacer(Modifier.weight(1f, true))
            if (currentRoute == BottomNavItem.UserList.screen_route) {
                var showAlertDialog by remember {
                    mutableStateOf(false)
                }
                if (showAlertDialog) {
                    AlertDialogChat(
                        onDismiss = { showAlertDialog = !showAlertDialog },
                        onConfirm = {
                            showAlertDialog = !showAlertDialog
                            userListViewModel.createFriendshipRegisterToFirebase(it)
                        })
                }
                ExtendedFloatingActionButton(
                    modifier = Modifier.padding(end = MaterialTheme.spacing.small),
                    onClick = {
                        showAlertDialog = !showAlertDialog
                    },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Text(text = "Add New Message")
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
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