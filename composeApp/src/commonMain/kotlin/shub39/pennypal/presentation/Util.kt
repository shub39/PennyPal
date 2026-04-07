package shub39.pennypal.presentation

import androidx.compose.foundation.shape.RoundedCornerShape
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
