package com.example.chatwithme.presentation.chat.chatrow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatwithme.presentation.chat.chatrow.chatbubble.ChatBubbleConstraints
import com.example.chatwithme.presentation.chat.chatrow.chatbubble.RecipientName
import com.example.chatwithme.presentation.chat.chatrow.chatbubble.TextMessageInsideBubble
import com.example.chatwithme.ui.theme.spacing

var isRecipientRegistered = true
var recipientOriginalName = "Some user"
@Composable
fun ReceivedMessageRow(
    text: String,
    opponentName: String,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
    messageTime: String,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.extraLarge,
                top = MaterialTheme.spacing.extraSmall,
                bottom = MaterialTheme.spacing.extraSmall
            )
    ) {
        //ChatBubble
        ChatBubbleConstraints(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomEnd = 16.dp, topEnd = 16.dp, bottomStart = 16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { },
            content = {
//                RecipientName(
//                    name = opponentName,
//                    isName = isRecipientRegistered,
//                    altName = recipientOriginalName
//                )
//                if (quotedMessage != null || quotedImage != null) {
//                    // 💬 Quoted message
//                    QuotedMessageAlt(
//                        modifier = Modifier
//                            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
//                            // 🔥 This is required to set Surface height before text is set
//                            .height(IntrinsicSize.Min)
//                            .background(Color(0xffECEFF1), shape = RoundedCornerShape(8.dp))
//                            .clip(shape = RoundedCornerShape(8.dp))
//                            .clickable {
//
//                            },
//                        quotedMessage = quotedMessage,
//                        quotedImage = quotedImage
//                    )
//                }
                TextMessageInsideBubble(
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.extraSmall,
                        top = MaterialTheme.spacing.extraSmall,
                        end = MaterialTheme.spacing.small,
                        bottom = MaterialTheme.spacing.extraSmall
                    ),
                    text = text,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                    messageStat = {
                        Text(
                            modifier = Modifier.padding(end = MaterialTheme.spacing.small),
                            text = messageTime,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                )
//                ChatFlexBoxLayout(
//                    modifier = Modifier.padding(start = 2.dp,  end = 4.dp),
//                    text = text,
//                    messageStat = {
////                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//                            Text(
//                                modifier = Modifier.padding(top = 1.dp, bottom = 1.dp, end = 4.dp),
//                                text = messageTime,
//                                fontSize = 12.sp
//                            )
////                        }
//                    }
//                )
            }
        )
    }
}