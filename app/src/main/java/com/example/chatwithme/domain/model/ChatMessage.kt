package com.example.chatwithme.domain.model

data class ChatMessage(
    val profileUUID: String = "",
    val message: String = "",
    val date: Long = 0,
    val status: String = ""
)
