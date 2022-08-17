package com.example.chatwithme.presentation.userlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.chatwithme.domain.model.FriendListRegister
import com.example.chatwithme.domain.model.FriendListRow
import com.example.chatwithme.domain.model.MessageStatus
import com.example.chatwithme.domain.model.User
import com.example.chatwithme.domain.usecase.chatScreen.LoadOpponentProfileFromFirebase
import com.example.chatwithme.presentation.chat.ChatScreenViewModel
import com.example.chatwithme.ui.theme.spacing
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AcceptPendingRequestList(
    item: FriendListRow,
    onclick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onclick()
            }
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.small,
                    vertical = MaterialTheme.spacing.small
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(60.dp),
//                    .padding(end = MaterialTheme.spacing.small),
                shape = CircleShape
            ) {
                if (item.userPictureUrl != "") {
                    Image(
                        painter = rememberAsyncImagePainter(item.userPictureUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .aspectRatio(f)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(4.dp)
                            .aspectRatio(1f)
                    )
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                val sdf = remember { SimpleDateFormat("hh:mm", Locale.ROOT) }
                if (item.lastMessage.status == MessageStatus.RECEIVED.toString() && item.lastMessage.profileUUID == item.userUUID) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(start = MaterialTheme.spacing.small),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = item.userEmail,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                            Text(
                                text = "Last Message: " + item.lastMessage.message,
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                        Column {
                            Text(
                                text = sdf.format(
                                    item.lastMessage.date
                                ),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )

                            Icon(
                                imageVector = Icons.Filled.MarkEmailUnread,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                    }
                } else {
                    val dateTimeControl: Long = 0
                    if (!item.lastMessage.date.equals(dateTimeControl)) {

                        if (item.lastMessage.profileUUID != item.userUUID) {
                            Row(
                                modifier = Modifier.fillMaxSize().padding(start = MaterialTheme.spacing.small),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = item.userEmail,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                                    Text(
                                        text = "Me: " + item.lastMessage.message,
                                        style = MaterialTheme.typography.titleSmall,

                                        )
                                }
                                Text(
                                    text = sdf.format(
                                        item.lastMessage.date
                                    ),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }

                        } else {
                            Row(
                                modifier = Modifier.fillMaxSize().padding(start = MaterialTheme.spacing.small),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = item.userEmail,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                                    Text(
                                        text = "Last Message: " + item.lastMessage.message,
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                }
                                Text(
                                    text = sdf.format(
                                        item.lastMessage.date
                                    ),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }

                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight().padding(start = MaterialTheme.spacing.small)
                        ) {
                            Text(
                                text = item.userEmail,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}