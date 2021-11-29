package me.arsam.github_client.data.repositories.impl

import androidx.lifecycle.LiveData
import me.arsam.github_client.data.db.dao.TokenDao
import me.arsam.github_client.data.models.Token
import me.arsam.github_client.data.repositories.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(private val tokenDao: TokenDao) : TokenRepository {
    override fun getAllTokens(): LiveData<List<Token>> {
        return tokenDao.getAllTokens()
    }

    override suspend fun insertToken(token: Token) {
        tokenDao.insertToken(token)
    }

    override suspend fun deleteToken(id: Int) {
        tokenDao.delete(id)
    }
}