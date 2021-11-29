package me.arsam.github_client.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import me.arsam.github_client.data.db.database.TokenDatabase
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesTokenDatabase(@ApplicationContext context: Context): TokenDatabase {
        return Room.databaseBuilder(context, TokenDatabase::class.java, TokenDatabase.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun providesNoteDao(tokenDatabase: TokenDatabase) = tokenDatabase.getTokenDao()
}