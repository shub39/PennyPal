package shub39.pennypal.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import platform.Foundation.NSHomeDirectory
import shub39.pennypal.data.database.AppDatabase
import shub39.pennypal.data.database.instantiateImpl

@Module
actual class PlatformModules {
    @Single
    fun provideDatabase(): AppDatabase {
        val dbFile = NSHomeDirectory() + "/${AppDatabase.DATABASE_NAME}"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile,
            factory = { AppDatabase::class.instantiateImpl() }
        )
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}