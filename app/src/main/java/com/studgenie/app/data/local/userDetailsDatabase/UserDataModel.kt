package com.studgenie.app.data.local.userDetailsDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDataModel(
    val number: String,
    val firstName: String,
    val lastName: String,
    val dob: String,
    val pictureUrl: String,
    val accountStatus: String,
    val maxDevices: Int,
    val userName: String,
    val studentId: Int,
    val instituteId: String,
    val email: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}