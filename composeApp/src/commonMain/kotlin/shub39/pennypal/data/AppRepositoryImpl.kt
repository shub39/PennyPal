package shub39.pennypal.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import shub39.pennypal.data.database.CategoryDao
import shub39.pennypal.data.database.TransactionDao
import shub39.pennypal.domain.AppRepository
import shub39.pennypal.domain.Category
import shub39.pennypal.domain.Transaction

@Single(binds = [AppRepository::class])
class AppRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : AppRepository {

    override fun getTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun upsertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun upsertCategory(category: Category) {
        categoryDao.insertCategory(category.toEntity())
    }

    override suspend fun deleteTransaction(transactionId: Long) {
        transactionDao.deleteTransactionById(transactionId)
    }

    override suspend fun deleteCategory(categoryId: Long) {
        categoryDao.deleteCategoryById(categoryId)
    }
}
