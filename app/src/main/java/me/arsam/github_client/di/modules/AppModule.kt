package me.arsam.github_client.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.arsam.github_client.data.db.database.AppDatabase
import me.arsam.github_client.utils.AuthorizationInterceptor
import me.arsam.github_client.utils.ConnectionUtils
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun provideRepositoryDao(appDatabase: AppDatabase) = appDatabase.getRepositoryDao()

    @Singleton
    @Provides
    fun providesUserDao(appDatabase: AppDatabase) = appDatabase.getUserDao()

    @Singleton
    @Provides
    fun providesConnectionUtils(application: Application) =
        ConnectionUtils(application.applicationContext)

    @Singleton
    @Provides
    fun providesApolloClient(): ApolloClient =
        ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(AuthorizationInterceptor())
                    .build()
            )
            .build()
}