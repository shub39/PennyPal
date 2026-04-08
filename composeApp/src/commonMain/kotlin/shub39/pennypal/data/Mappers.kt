package shub39.pennypal.data

import shub39.pennypal.data.database.CategoryEntity
import shub39.pennypal.data.database.TransactionEntity
import shub39.pennypal.domain.Category
import shub39.pennypal.domain.Transaction

fun CategoryEntity.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        colorArgb = colorArgb,
        categoryIcon = categoryIcon
    )
}

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        colorArgb = colorArgb,
        categoryIcon = categoryIcon
    )
}

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        categoryId = categoryId,
        amount = amount,
        date = date,
        note = note,
        recurrence = recurrence,
        transactionType = transactionType
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        categoryId = categoryId,
        amount = amount,
        date = date,
        note = note,
        recurrence = recurrence,
        transactionType = transactionType
    )
}
