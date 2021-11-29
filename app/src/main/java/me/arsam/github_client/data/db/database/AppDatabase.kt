package me.arsam.github_client.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.arsam.github_client.data.db.LocalDateTimeConverter
import me.arsam.github_client.data.db.dao.RepositoryDao
import me.arsam.github_client.data.models.Repository

@Database(entities = [Repository::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRepositoryDao(): RepositoryDao

    companion object {
        const val DB_NAME = "app_database.db"

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }
}