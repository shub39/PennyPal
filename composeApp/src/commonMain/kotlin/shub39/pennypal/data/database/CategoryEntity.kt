package shub39.pennypal.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import shub39.pennypal.domain.CategoryIcon

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val colorArgb: Int,
    val categoryIcon: CategoryIcon,
)
