package com.example.chatwithme.presentation.commonComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatwithme.ui.theme.spacing

@Composable
fun LogOutCustomText(
    entry: String = "Log Out",
    fontSize: TextUnit = 10.sp,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
    ) {
        Text(
            text = "Log Out",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onError
        )
    }

//    Box(
//        modifier = Modifier
//            .clip(RoundedCornerShape(percent = 50))
//            .background(
//                MaterialTheme.colorScheme.surface
//            )
//            .height(26.dp)
//            .fillMaxWidth()
//            .clickable {
//                onClick()
//            }) {
//        Text(
//            modifier = Modifier.align(Alignment.Center),
//            text = entry,
//            fontSize = fontSize,
//            color = Color.Red
//        )
//    }
}