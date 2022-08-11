package com.example.chatwithme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatwithme.domain.model.UserStatus
import com.example.chatwithme.domain.usecase.authScreen.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: AuthUseCases
) : ViewModel() {
    fun setUserStatusToFirebase(userStatus: UserStatus){
        viewModelScope.launch {
//            useCases.setu
        }
    }
}