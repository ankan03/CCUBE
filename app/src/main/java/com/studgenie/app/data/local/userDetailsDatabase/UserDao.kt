package com.studgenie.app.data.local.userDetailsDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUserData(userDataModel: UserDataModel)

    @Query("UPDATE UserDataModel SET number=:number, userName = :username,email = :email WHERE id=:pid")
    suspend fun updateUserData(number: String, username: String, email: String, pid: Int)

    @Query("SELECT * FROM UserDataModel ORDER BY id DESC")
    fun getUserData(): LiveData<List<UserDataModel>>


    @Query("DELETE FROM UserDataModel")
    suspend fun deleteUserData()
}