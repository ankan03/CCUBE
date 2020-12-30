package com.studgenie.app.data.local.userStatusDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserStatusDao {
    @Insert
    suspend fun addUserStatus(userStatusModel: UserStatusModel)

    @Query("UPDATE UserStatusModel SET status=:status WHERE id=:pid")
    suspend fun updateUserStatus(status: String, pid: Int)

    @Query("SELECT * FROM UserStatusModel ORDER BY id DESC")
    fun getUserStatus(): LiveData<List<UserStatusModel>>

    @Query("DELETE FROM UserStatusModel")
    suspend fun deleteUserStatus()
}