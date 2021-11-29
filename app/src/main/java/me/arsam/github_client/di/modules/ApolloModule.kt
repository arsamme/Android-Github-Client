package me.arsam.github_client.di.modules

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.arsam.github_client.utils.AuthorizationInterceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {
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