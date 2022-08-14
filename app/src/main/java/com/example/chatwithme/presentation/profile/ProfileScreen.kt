package com.example.chatwithme.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatwithme.core.SnackbarController
import com.example.chatwithme.domain.model.User
import com.example.chatwithme.domain.model.UserStatus
import com.example.chatwithme.presentation.bottomnavigation.BottomNavItem
import com.example.chatwithme.presentation.commonComponents.LogOutCustomText
import com.example.chatwithme.presentation.profile.components.ProfileAppBar
import com.example.chatwithme.ui.theme.spacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    keyboardController: SoftwareKeyboardController
) {

    val snackbarMessage = profileViewModel.toastMessage.value
    LaunchedEffect(key1 = snackbarMessage) {
        if (snackbarMessage != "") {
            SnackbarController(this).showSnackbar(snackbarHostState, snackbarMessage, "Close")
        }
    }

    var isLoading by remember {
        mutableStateOf(false)
    }
    isLoading = profileViewModel.isLoading.value

    var userDataFromFirebase by remember {
        mutableStateOf(User())
    }
    userDataFromFirebase = profileViewModel.userDataStateFromFirebase.value

    var email by remember {
        mutableStateOf("")
    }
    email = userDataFromFirebase.userEmail
    var name by remember {
        mutableStateOf("")
    }
    name = userDataFromFirebase.userName
    var surName by remember {
        mutableStateOf("")
    }
    surName = userDataFromFirebase.userSurName
    var bio by remember { mutableStateOf("") }
    bio = userDataFromFirebase.userBio

    var phoneNumber by remember { mutableStateOf("") }
    phoneNumber = userDataFromFirebase.userPhoneNumber

    var userDataPictureUrl by remember { mutableStateOf("") }
    userDataPictureUrl = userDataFromFirebase.userProfilePictureUrl

//    val isUserSignOut = profileViewModel.isUserSignOutState.value
//    LaunchedEffect(key1 = isUserSignOut) {
//        if (isUserSignOut) {
//            navController.popBackStack()
//            navController.navigate(BottomNavItem.SignIn.fullRoute)
//        }
//    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ProfileAppBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

//        Surface(modifier = Modifier.fillMaxSize()) {
//            LogOutCustomText {
//                profileViewModel.setUserStatusToFirebaseAndSignOut(UserStatus.OFFLINE)
//            }
//        }
        }
    }


}