package me.arsam.github_client.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.arsam.github_client.data.db.dao.TokenDao
import me.arsam.github_client.data.models.Token

@Database(entities = [Token::class], version = 1)
abstract  class TokenDatabase :RoomDatabase(){
    abstract fun getTokenDao(): TokenDao

    companion object {
        const val DB_NAME = "token_database.db"
        @Volatile private var instance: TokenDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TokenDatabase::class.java,
            DB_NAME
        ).build()
    }
}