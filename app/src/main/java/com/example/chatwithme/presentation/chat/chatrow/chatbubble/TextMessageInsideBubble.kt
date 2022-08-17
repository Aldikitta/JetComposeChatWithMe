package com.example.chatwithme.presentation.chat.chatrow.chatbubble

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.chatwithme.presentation.chat.chatrow.chatlayout.ChatRowData
import com.example.chatwithme.presentation.chat.chatrow.chatlayout.calculateChatWidthAndHeight


@Composable
fun TextMessageInsideBubble(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current,
    messageStat: @Composable () -> Unit,
    onMeasure: ((ChatRowData) -> Unit)? = null
) {
    val chatRowData = remember { ChatRowData() }
    val content = @Composable {

        Text(
            modifier = modifier
//                .padding(horizontal = 6.dp, vertical = 6.dp)
                .wrapContentSize(),
            text = text,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            style = style,
            maxLines = maxLines,
            onTextLayout = { textLayoutResult: TextLayoutResult ->
                // maxWidth of text constraint returns parent maxWidth - horizontal padding
                chatRowData.lineCount = textLayoutResult.lineCount
                chatRowData.lastLineWidth =
                    textLayoutResult.getLineRight(chatRowData.lineCount - 1)
                chatRowData.textWidth = textLayoutResult.size.width
            }
        )

        messageStat()
    }

    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        if (measurables.size != 2)
            throw IllegalArgumentException("There should be 2 components for this layout")

//        println("⚠️ CHAT constraints: $constraints")

        val placeables: List<Placeable> = measurables.map { measurable ->
            // Measure each child maximum constraints since message can cover all of the available
            // space by parent
            measurable.measure(Constraints(0, constraints.maxWidth))
        }

        val message = placeables.first()
        val status = placeables.last()

        // calculate chat row dimensions are not  based on message and status positions
        if ((chatRowData.rowWidth == 0 || chatRowData.rowHeight == 0) || chatRowData.text != text) {
            // Constrain with max width instead of longest sibling
            // since this composable can be longest of siblings after calculation
            chatRowData.parentWidth = constraints.maxWidth
            calculateChatWidthAndHeight(text, chatRowData, message, status)
            // Parent width of this chat row is either result of width calculation
            // or quote or other sibling width if they are longer than calculated width.
            // minWidth of Constraint equals (text width + horizontal padding)
            chatRowData.parentWidth =
                chatRowData.rowWidth.coerceAtLeast(minimumValue = constraints.minWidth)
        }

//        println("⚠️⚠️ CHAT after calculation-> CHAT_ROW_DATA: $chatRowData")

        // Send measurement results if requested by Composable
        onMeasure?.invoke(chatRowData)

        layout(width = chatRowData.parentWidth, height = chatRowData.rowHeight) {

            message.placeRelative(0, 0)
            // set left of status relative to parent because other elements could result this row
            // to be long as longest composable
            status.placeRelative(
                chatRowData.parentWidth - status.width,
                chatRowData.rowHeight - status.height
            )
        }
    }
}