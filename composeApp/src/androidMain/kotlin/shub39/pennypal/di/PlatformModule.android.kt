package shub39.pennypal.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import shub39.pennypal.data.database.AppDatabase

@Module
actual class PlatformModules {
    @Single
    fun provideDatabase(context: Context): AppDatabase {
        val dbFile = context.getDatabasePath(AppDatabase.DATABASE_NAME)
        return Room.databaseBuilder<AppDatabase>(context = context, name = dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}
