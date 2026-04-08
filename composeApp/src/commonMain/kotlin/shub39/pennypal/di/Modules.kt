package shub39.pennypal.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.annotation.Factory
import org.koin.core.annotation.KoinApplication
import shub39.pennypal.data.database.AppDatabase
import shub39.pennypal.data.database.TransactionDao
import shub39.pennypal.data.database.CategoryDao
import shub39.pennypal.domain.AppRepository
import shub39.pennypal.data.AppRepositoryImpl
import shub39.pennypal.viewmodel.HomeViewModel

@Module(includes = [PlatformModules::class])
@KoinApplication
@ComponentScan("shub39.pennypal")
class Modules {
    @Single
    fun provideTransactionDao(database: AppDatabase): TransactionDao = database.transactionDao()

    @Single
    fun provideCategoryDao(database: AppDatabase): CategoryDao = database.categoryDao()

    @Single
    fun provideAppRepository(transactionDao: TransactionDao, categoryDao: CategoryDao): AppRepository {
        return AppRepositoryImpl(transactionDao, categoryDao)
    }
}