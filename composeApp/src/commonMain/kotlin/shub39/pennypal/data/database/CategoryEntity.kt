package shub39.pennypal.data.database

import shub39.pennypal.domain.CategoryIcon

data class CategoryEntity(
    val id: Long = 0,
    val name: String,
    val colorArgb: Int,
    val categoryIcon: CategoryIcon,
)
