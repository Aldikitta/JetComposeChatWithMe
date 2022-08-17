package com.example.chatwithme.presentation.chat.chatrow.chatlayout

import androidx.compose.ui.layout.Placeable

fun calculateChatWidthAndHeight(
    text: String,
    chatRowData: ChatRowData,
    message: Placeable,
    status: Placeable?,
) {

    if (status != null) {

        val lineCount = chatRowData.lineCount
        val lastLineWidth = chatRowData.lastLineWidth
        val parentWidth = chatRowData.parentWidth

        val padding = (message.measuredWidth - chatRowData.textWidth) / 2
//        println(
//            "🌽 CHAT INIT calculate() text: $text\n" +
//                    "lineCount: $lineCount, parentWidth: $parentWidth, lastLineWidth: $lastLineWidth\n" +
//                    "MESSAGE width: ${message.width}, measured: ${message.measuredWidth}," +
//                    " textWidth: ${chatRowData.textWidth} padding: $padding\n" +
//                    "STATUS width: ${status.width}, measured: ${status.measuredWidth}, " +
//                    "(stat +last): ${lastLineWidth + status.measuredWidth}\n"
//        )

        // Multiple lines and last line and status is longer than text size and right padding
        if (lineCount > 1 && lastLineWidth + status.measuredWidth >= chatRowData.textWidth + padding) {
            chatRowData.rowWidth = message.measuredWidth
            chatRowData.rowHeight = message.measuredHeight + status.measuredHeight
            chatRowData.measuredType = 0
//            println("🤔 CHAT calculate() 0 for ${chatRowData.textWidth + padding}")
        } else if (lineCount > 1 && lastLineWidth + status.measuredWidth < chatRowData.textWidth + padding) {
            // Multiple lines and last line and status is shorter than text size and right padding
            chatRowData.rowWidth = message.measuredWidth
            chatRowData.rowHeight = message.measuredHeight
            chatRowData.measuredType = 1
//            println("🔥 CHAT calculate() 1 for ${message.measuredWidth - padding}")
        } else if (lineCount == 1 && message.width + status.measuredWidth >= parentWidth) {
            chatRowData.rowWidth = message.measuredWidth
            chatRowData.rowHeight = message.measuredHeight + status.measuredHeight
            chatRowData.measuredType = 2
//            println("🎃 CHAT calculate() 2")
        } else {
            chatRowData.rowWidth = message.measuredWidth + status.measuredWidth
            chatRowData.rowHeight = message.measuredHeight
            chatRowData.measuredType = 3
//            println("🚀 CHAT calculate() 3")
        }
    } else {
        chatRowData.rowWidth = message.width
        chatRowData.rowHeight = message.height
    }
}