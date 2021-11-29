package me.arsam.github_client.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
data class Token(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val token: String,
)