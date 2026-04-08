package shub39.pennypal.data.database

import androidx.room.TypeConverter
import kotlin.time.Instant
import shub39.pennypal.domain.CategoryIcon
import shub39.pennypal.domain.Recurrence
import shub39.pennypal.domain.TransactionType

class Converters {
    @TypeConverter
    fun fromInstant(value: Instant): Long {
        return value.toEpochMilliseconds()
    }

    @TypeConverter
    fun toInstant(value: Long): Instant {
        return Instant.fromEpochMilliseconds(value)
    }

    @TypeConverter
    fun fromRecurrence(value: Recurrence): String {
        return value.name
    }

    @TypeConverter
    fun toRecurrence(value: String): Recurrence {
        return Recurrence.valueOf(value)
    }

    @TypeConverter
    fun fromTransactionType(value: TransactionType): String {
        return value.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }

    @TypeConverter
    fun fromCategoryIcon(value: CategoryIcon): String {
        return value.name
    }

    @TypeConverter
    fun toCategoryIcon(value: String): CategoryIcon {
        return CategoryIcon.valueOf(value)
    }
}
