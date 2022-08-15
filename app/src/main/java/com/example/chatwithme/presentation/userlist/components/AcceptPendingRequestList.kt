package com.example.chatwithme.presentation.userlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.chatwithme.domain.model.FriendListRow
import com.example.chatwithme.domain.model.MessageStatus
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AcceptPendingRequestList(
    item: FriendListRow,
    onclick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onclick
            }
    ) {
        Row(
            modifier = Modifier.height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape
            ) {
                if (item.userPictureUrl != "") {
                    Image(
                        painter = rememberAsyncImagePainter(item.userPictureUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .aspectRatio(1f)
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
            Box() {
                val sdf = remember { SimpleDateFormat("hh:mm", Locale.ROOT) }
                if (item.lastMessage.status == MessageStatus.RECEIVED.toString() && item.lastMessage.profileUUID == item.userUUID) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(3f)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = item.userEmail)
                            Text(
                                text = "Last Message: " + item.lastMessage.message + " " + "(${
                                    sdf.format(
                                        item.lastMessage.date
                                    )
                                })",
                                fontSize = 10.sp,
                                modifier = Modifier.padding(2.dp)
                            )


                        }
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "New Message",
                                color = Color.Red,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .padding(2.dp)
                                    .align(Alignment.CenterHorizontally)
                            )

                            Icon(
                                imageVector = Icons.Filled.MarkEmailUnread,
                                tint = Color.Red,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(2.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                    }
                } else {
                    val dateTimeControl: Long = 0
                    if (!item.lastMessage.date.equals(dateTimeControl)) {

                        if (item.lastMessage.profileUUID != item.userUUID) {
                            Column {
                                Text(text = item.userEmail)
                                Text(
                                    text = "Me: " + item.lastMessage.message + " " + "(${
                                        sdf.format(
                                            item.lastMessage.date
                                        )
                                    })",
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(2.dp)
                                )
                            }
                        } else {
                            Column {
                                Text(text = item.userEmail)
                                Text(
                                    text = "Last Message: " + item.lastMessage.message + " " + "(${
                                        sdf.format(
                                            item.lastMessage.date
                                        )
                                    })",
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(2.dp)
                                )
                            }
                        }

                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = item.userEmail,
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