package shub39.pennypal.data

import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import shub39.pennypal.data.database.CategoryDao
import shub39.pennypal.data.database.TransactionDao
import shub39.pennypal.domain.AppRepository
import shub39.pennypal.domain.Category
import shub39.pennypal.domain.CategoryIcon
import shub39.pennypal.domain.Recurrence
import shub39.pennypal.domain.Transaction
import shub39.pennypal.domain.TransactionType

@Single(binds = [AppRepository::class])
class AppRepositoryImpl(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
) : AppRepository {

    override fun getTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { entities -> entities.map { it.toDomain() } }
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

    override suspend fun deleteAllData() {
        transactionDao.deleteAllTransactions()
        categoryDao.deleteAllCategories()
    }

    override suspend fun addDummyData() {
        val categories =
            listOf(
                Category(
                    name = "Salary",
                    colorArgb = 0xFF4CAF50.toInt(),
                    categoryIcon = CategoryIcon.MISC,
                ),
                Category(
                    name = "Freelance",
                    colorArgb = 0xFF8BC34A.toInt(),
                    categoryIcon = CategoryIcon.MISC,
                ),
                Category(
                    name = "Food",
                    colorArgb = 0xFFFF5252.toInt(),
                    categoryIcon = CategoryIcon.FOOD,
                ),
                Category(
                    name = "Transport",
                    colorArgb = 0xFF448AFF.toInt(),
                    categoryIcon = CategoryIcon.TRANSPORT,
                ),
                Category(
                    name = "Shopping",
                    colorArgb = 0xFFFF4081.toInt(),
                    categoryIcon = CategoryIcon.SHOPPING,
                ),
            )

        categories.forEach { upsertCategory(it) }

        val savedCategories = getCategories().first()
        if (savedCategories.isEmpty()) return

        val now = Clock.System.now()
        val transactions = mutableListOf<Transaction>()

        val salaryCat = savedCategories.first { it.name == "Salary" }
        val freelanceCat = savedCategories.first { it.name == "Freelance" }
        val foodCat = savedCategories.first { it.name == "Food" }
        val transportCat = savedCategories.first { it.name == "Transport" }
        val shoppingCat = savedCategories.first { it.name == "Shopping" }

        // Add transactions for the last 5 months
        for (month in 0 until 5) {
            val monthAnchor = now.minus((month * 30).days)

            // Monthly Income: Salary (Repeating every month)
            transactions.add(
                Transaction(
                    categoryId = salaryCat.id,
                    amount = (4000..5000).random().toDouble(),
                    date = monthAnchor,
                    note = "Salary - Month ${5 - month}",
                    recurrence = Recurrence.MONTHLY,
                    transactionType = TransactionType.INCOME,
                )
            )

            // Weekly Income: Freelance (Repeating 4 times a month)
            for (week in 0 until 4) {
                transactions.add(
                    Transaction(
                        categoryId = freelanceCat.id,
                        amount = (200..500).random().toDouble(),
                        date = monthAnchor.minus((week * 7).days),
                        note = "Freelance Payout - Week ${4 - week}",
                        recurrence = Recurrence.WEEKLY,
                        transactionType = TransactionType.INCOME,
                    )
                )
            }

            // Weekly Expense: Transport (Repeating 4 times a month)
            for (week in 0 until 4) {
                transactions.add(
                    Transaction(
                        categoryId = transportCat.id,
                        amount = (50..100).random().toDouble(),
                        date = monthAnchor.minus((week * 7).days),
                        note = "Weekly Transport Pass",
                        recurrence = Recurrence.WEEKLY,
                        transactionType = TransactionType.EXPENSE,
                    )
                )
            }

            // Daily Expenses: Food (Continuous daily occurrence)
            for (day in 0 until 30) {
                val transactionDate = monthAnchor.minus(day.days)
                transactions.add(
                    Transaction(
                        categoryId = foodCat.id,
                        amount = (10..40).random().toDouble(),
                        date = transactionDate,
                        note = "Daily Meals",
                        recurrence = Recurrence.NONE,
                        transactionType = TransactionType.EXPENSE,
                    )
                )

                // Occasional Shopping
                if ((1..10).random() == 1) {
                    transactions.add(
                        Transaction(
                            categoryId = shoppingCat.id,
                            amount = (50..300).random().toDouble(),
                            date = transactionDate,
                            note = "Shopping Trip",
                            recurrence = Recurrence.NONE,
                            transactionType = TransactionType.EXPENSE,
                        )
                    )
                }
            }
        }

        transactions.forEach { upsertTransaction(it) }
    }
}
