package shub39.pennypal.domain

import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getTransactions(): Flow<List<Transaction>>
    fun getCategories(): Flow<List<Category>>
    suspend fun upsertTransaction(transaction: Transaction)
    suspend fun upsertCategory(category: Category)
    suspend fun deleteTransaction(transactionId: Long)
    suspend fun deleteCategory(categoryId: Long)
}