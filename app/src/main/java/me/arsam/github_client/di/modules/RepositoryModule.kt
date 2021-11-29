package me.arsam.github_client.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import me.arsam.github_client.data.db.dao.TokenDao
import me.arsam.github_client.data.repositories.impl.TokenRepositoryImpl

@InstallIn(ActivityRetainedComponent::class)
@Module
class RepositoryModule {
    @Provides
    fun providesTokenRepository(tokenDao: TokenDao) = TokenRepositoryImpl(tokenDao)
}