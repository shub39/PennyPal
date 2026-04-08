package shub39.pennypal.presentation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

private const val CONNECTED_CORNER_RADIUS = 4
private const val END_CORNER_RADIUS = 16

fun leadingItemShape(
    topRadius: Int = END_CORNER_RADIUS,
    bottomRadius: Int = CONNECTED_CORNER_RADIUS,
): Shape =
    RoundedCornerShape(
        topStart = topRadius.dp,
        topEnd = topRadius.dp,
        bottomEnd = bottomRadius.dp,
        bottomStart = bottomRadius.dp,
    )

fun middleItemShape(radius: Int = CONNECTED_CORNER_RADIUS): Shape =
    RoundedCornerShape(
        topStart = radius.dp,
        topEnd = radius.dp,
        bottomStart = radius.dp,
        bottomEnd = radius.dp,
    )

fun endItemShape(
    topRadius: Int = CONNECTED_CORNER_RADIUS,
    bottomRadius: Int = END_CORNER_RADIUS,
): Shape =
    RoundedCornerShape(
        topStart = topRadius.dp,
        topEnd = topRadius.dp,
        bottomEnd = bottomRadius.dp,
        bottomStart = bottomRadius.dp,
    )

fun detachedItemShape(radius: Int = END_CORNER_RADIUS): Shape = RoundedCornerShape(radius.dp)

val CategoryColors =
    listOf(
        Color(0xFFE57373), // Red
        Color(0xFFF06292), // Pink
        Color(0xFFBA68C8), // Purple
        Color(0xFF9575CD), // Deep Purple
        Color(0xFF7986CB), // Indigo
        Color(0xFF64B5F6), // Blue
        Color(0xFF4FC3F7), // Light Blue
        Color(0xFF4DD0E1), // Cyan
        Color(0xFF4DB6AC), // Teal
        Color(0xFF81C784), // Green
    )
