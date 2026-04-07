package shub39.pennypal.domain

data class Category(
    val id: Long,
    val name: String,
    val colorArgb: Int,
    val categoryIcon: CategoryIcon
)
