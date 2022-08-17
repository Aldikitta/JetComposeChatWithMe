//package com.example.chatwithme.presentation.chat.chatrow.quotedmessage
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.Layout
//import androidx.compose.ui.layout.Placeable
//import androidx.compose.ui.layout.layoutId
//import androidx.compose.ui.unit.Constraints
//import androidx.compose.ui.unit.IntSize
//
///**
// * Row for storing quote title, message or image description and image itself.
// * [image] is positioned end of this layout.
// */
//@Composable
//fun QuoteImageRow(
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit,
//    image: @Composable (() -> Unit)? = null
//) {
//
//    val finalContent = @Composable {
//        if (image != null) {
//            content()
//            image.invoke()
//        } else {
//            content()
//        }
//    }
//
////    println("⛺️ CustomRow() START...")
//
//    Layout(modifier = modifier, content = finalContent) { measurables, constraints ->
//
////        println("⛺️ CustomRow() Layout measurables: ${measurables.size}, constraints: $constraints")
//
//        var imageIndex = -1
//
//        val placeables = measurables.mapIndexed { index, measurable ->
//
//            if (measurable.layoutId == "image") {
//                imageIndex = index
////                println("🍭CustomRow() CUSTOM ROW INDEX: $imageIndex")
//            }
//            measurable.measure(Constraints(0, constraints.maxWidth, 0, constraints.maxHeight))
//        }
//
//        val size =
//            placeables.fold(IntSize.Zero) { current: IntSize, placeable: Placeable ->
//
////                println("🏠 CustomRow() PLACEABLE width: ${placeable.width}, height: ${placeable.height}")
//                IntSize(
//                    width = current.width + placeable.width,
//                    height = maxOf(current.height, placeable.height)
//                )
//            }
//
//
//        val width = size.width.coerceAtLeast(constraints.minWidth)
//
//        var x = 0
//        layout(width, size.height) {
//
////            println("🚐 CustomRow() layout() TOTAL SIZE: $size, width: $width")
//
//            placeables.forEachIndexed { index: Int, placeable: Placeable ->
//                if (index != imageIndex) {
//                    placeable.placeRelative(x, 0)
//                    x += placeable.width
//                } else {
//                    placeable.placeRelative(width - placeable.width, 0)
//                }
//            }
//        }
//    }
//}