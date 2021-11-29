package me.arsam.github_client.di.modules

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import me.arsam.github_client.data.db.dao.RepositoryDao
import me.arsam.github_client.data.repositories.RepoRepository

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun providesRepoRepository(repositoryDao: RepositoryDao, apolloClient: ApolloClient) =
        RepoRepository(repositoryDao, apolloClient)
}