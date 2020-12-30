package com.studgenie.app.data.local.userStatusDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserStatusModel(
    var status: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}