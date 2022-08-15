package com.example.chatwithme.presentation.userlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatwithme.domain.model.FriendListRegister
import com.example.chatwithme.domain.model.FriendListRow
import com.example.chatwithme.domain.usecase.userListScreen.UserListScreenUseCases
import com.example.chatwithme.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListScreenUseCases: UserListScreenUseCases
) : ViewModel() {
    var pendingFriendRequestList = mutableStateOf<List<FriendListRegister>>(listOf())
        private set

    var acceptedFriendRequestList = mutableStateOf<List<FriendListRow>>(listOf())
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    var toastMessage = mutableStateOf("")
        private set

    fun refreshingFriendList() {
        viewModelScope.launch {
            isRefreshing.value = true

        }
    }

    fun createFriendshipRegisterToFirebase(acceptorEmail: String) {
        //Search User -> Check Chat Room -> Create Chat Room -> Check FriendListRegister -> Create FriendListRegister
        viewModelScope.launch {
            userListScreenUseCases.searchUserFromFirebase.invoke(acceptorEmail)
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {
                            toastMessage.value = ""
                        }
                        is Response.Success -> {
                            if (response.data != null) {

                            }
                        }
                        is Response.Error -> {}
                    }

                }
        }
    }

    fun acceptPendingFriendRequestToFirebase(registerUUID: String) {
        viewModelScope.launch {
            userListScreenUseCases.acceptPendingFriendRequestToFirebase.invoke(registerUUID)
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {
                            toastMessage.value = ""
                        }
                        is Response.Success -> {
                            toastMessage.value = "Friend Request Accepted"
                        }
                        is Response.Error -> {}
                    }
                }
        }
    }

    fun cancelPendingFriendRequestToFirebase(registerUUID: String) {
        viewModelScope.launch {
            userListScreenUseCases.rejectPendingFriendRequestToFirebase.invoke(registerUUID)
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {
                            toastMessage.value = ""
                        }
                        is Response.Success -> {
                            toastMessage.value = "Friend Request Canceled"
                        }
                        is Response.Error -> {}
                    }
                }
        }
    }

    private fun loadAcceptFriendRequestListFromFirebase() {
        viewModelScope.launch {
            userListScreenUseCases.loadAcceptedFriendRequestListFromFirebase.invoke()
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {}
                        is Response.Success -> {
                            if (response.data.isNotEmpty()) {
                                acceptedFriendRequestList.value = response.data
                            }
                        }
                        is Response.Error -> {}
                    }
                }
        }
    }

    private fun loadPendingFriendRequestListFromFirebase() {
        viewModelScope.launch {
            userListScreenUseCases.loadPendingFriendRequestListFromFirebase.invoke()
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {}
                        is Response.Success -> {
                            pendingFriendRequestList.value = response.data
                        }
                        is Response.Error -> {}
                    }
                }
        }
    }
}