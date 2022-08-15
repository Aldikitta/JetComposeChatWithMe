package com.example.chatwithme.presentation.userlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chatwithme.domain.model.FriendListRegister

@Composable
fun PendingFriendRequestList(
    item: FriendListRegister,
    onAcceptClick:() -> Unit = {},
    onCancelClick:() -> Unit = {}){
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
    ){
        Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier) {
                Text(text = item.requesterEmail,
                    modifier = Modifier
                        .padding(10.dp))
            }
            Icon(
                imageVector = Icons.Filled.Done,
                tint = Color.Green,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onAcceptClick()
                    }
                    .align(Alignment.CenterVertically))
            Icon(
                imageVector = Icons.Filled.Clear,
                tint = Color.Red,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onCancelClick()
                    }
                    .align(Alignment.CenterVertically))
        }
    }
}