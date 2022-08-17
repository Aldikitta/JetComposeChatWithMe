package com.example.chatwithme.presentation.chat.chatrow

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatwithme.domain.model.MessageStatus
import com.example.chatwithme.ui.theme.spacing

@Composable
fun MessageTimeText(
    modifier: Modifier = Modifier,
    messageTime: String,
    messageStatus: MessageStatus
) {
    val messageStat = remember {
        messageStatus
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = messageTime,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )

        Icon(
            modifier = Modifier
                .size(18.dp)
                .padding(start = 4.dp),
            imageVector = Icons.Default.DoneAll,
//        when (messageStatus) {
//                MessageStatus.PENDING -> {
//                    Icons.Default.DoneAll
//                }
//                MessageStatus.RECEIVED -> {
//                    Icons.Default.DoneAll
//                }
//                MessageStatus.READ ->{
//                    Icons.Default.DoneAll
//                }
//            },
            tint = if (messageStatus == MessageStatus.READ) Color.Blue
            else Color.Gray,
            contentDescription = "messageStatus"
        )

    }
}