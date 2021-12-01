package me.arsam.github_client.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

@Entity(tableName = "repositories")
data class Repository(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "forks_count")
    val forksCount: Int,
    @ColumnInfo(name = "stars_count")
    val starsCount: Int,
    @ColumnInfo(name = "watchers_count")
    val watchersCount: Int,
)