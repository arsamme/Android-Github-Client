package me.arsam.github_client.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.arsam.github_client.data.models.Repository

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repositories ORDER BY created_at DESC")
    suspend fun getAll(): List<Repository>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repository: Repository)

    @Query("DELETE FROM repositories")
    suspend fun deleteAll()
}