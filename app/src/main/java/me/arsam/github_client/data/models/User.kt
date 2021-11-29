package me.arsam.github_client.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val name: String?,
    val email: String,
    val username: String,
    val bio: String?,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,
    @ColumnInfo(name = "followers_count")
    val followersCount: Int,
    @ColumnInfo(name = "followings_count")
    val followingsCount: Int
)