package me.arsam.github_client.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.arsam.github_client.data.models.Token

@Dao
interface TokenDao {
    @Query("SELECT * FROM tokens")
    fun getAllTokens(): LiveData<List<Token>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToken(token: Token)

    @Query("DELETE FROM tokens WHERE id = :id")
    suspend fun delete(id: Int)
}