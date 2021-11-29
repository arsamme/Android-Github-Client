package me.arsam.github_client.data.repositories

import androidx.lifecycle.LiveData
import me.arsam.github_client.data.models.Token

interface TokenRepository {
    fun getAllTokens(): LiveData<List<Token>>
    suspend fun insertToken(token: Token)
    suspend fun deleteToken(id: Int)
}