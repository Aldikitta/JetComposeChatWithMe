package com.example.chatwithme.presentation.chat.chatAppBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

@Composable
fun ProfilePictureDialog(
    profilePictureUrl: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(shape = RoundedCornerShape(12.dp)) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .padding(8.dp)
            ) {

                Image(
                    painter = rememberAsyncImagePainter(profilePictureUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(260.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}