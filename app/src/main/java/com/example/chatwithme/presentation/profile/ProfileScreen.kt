package com.example.chatwithme.presentation.profile

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatwithme.core.SnackbarController
import com.example.chatwithme.domain.model.User
import com.example.chatwithme.domain.model.UserStatus
import com.example.chatwithme.presentation.bottomnavigation.BottomNavItem
import com.example.chatwithme.presentation.commonComponents.LogOutCustomText
import com.example.chatwithme.presentation.profile.components.ChooseProfilePicFromGallery
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

    val toastMessage = profileViewModel.toastMessage.value
    LaunchedEffect(key1 = toastMessage){
        if(toastMessage != ""){
            SnackbarController(this).showSnackbar(snackbarHostState,toastMessage, "Close")
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

    val scrollState = rememberScrollState()

//    val isUserSignOut = profileViewModel.isUserSignOutState.value
//    LaunchedEffect(key1 = isUserSignOut) {
//        if (isUserSignOut) {
//            navController.popBackStack()
//            navController.navigate(BottomNavItem.SignIn.fullRoute)
//        }
//    }

    var updatedImage by remember {
        mutableStateOf<Uri?>(null)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusable(true)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { keyboardController.hide() })
            }
    ) {
        ProfileAppBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.size(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Text(text = "Mail: $email")
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    ChooseProfilePicFromGallery(userDataPictureUrl) {
                        if (it != null) {
                            updatedImage = it
//                            profileViewModel.uploadPictureToFirebase(it)
                        }
                    }
                }
                Button(
                    onClick = {
                        updatedImage?.let { profileViewModel.uploadPictureToFirebase(it) }
                    },
                    enabled = updatedImage != null
                ) {
                    Text(text = "Save")
                }
            }
//        Surface(modifier = Modifier.fillMaxSize()) {
//            LogOutCustomText {
//                profileViewModel.setUserStatusToFirebaseAndSignOut(UserStatus.OFFLINE)
//            }
//        }
        }
    }


}