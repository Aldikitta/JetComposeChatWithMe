package com.example.chatwithme.presentation.userlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatwithme.domain.model.FriendListRegister
import com.example.chatwithme.domain.model.FriendListRow
import com.example.chatwithme.domain.usecase.userListScreen.UserListScreenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
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
}