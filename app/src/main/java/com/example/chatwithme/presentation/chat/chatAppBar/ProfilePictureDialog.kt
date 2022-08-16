package com.example.chatwithme.presentation.chat.chatAppBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.example.chatwithme.ui.theme.spacing

@Composable
fun ProfilePictureDialog(
    profilePictureUrl: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Card() {
            Image(
                painter = rememberAsyncImagePainter(profilePictureUrl),
                contentDescription = null,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.large)
                    .size(260.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}