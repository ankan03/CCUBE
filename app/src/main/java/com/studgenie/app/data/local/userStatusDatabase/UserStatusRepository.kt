package com.studgenie.app.data.local.userStatusDatabase

import androidx.lifecycle.LiveData


//class UserStatusRepository {
//}

class UserStatusRepository(private val userStatusDao: UserStatusDao) {
    val readAllData: LiveData<List<UserStatusModel>> = userStatusDao.getUserStatus()

    suspend fun addUserStatus(userStatusModel: UserStatusModel) {
        userStatusDao.addUserStatus(userStatusModel)
    }

    suspend fun updateUserStatus(status: String, pid: Int) {
        userStatusDao.updateUserStatus(status, pid)
    }

    suspend fun deleteUserStatus() {
        userStatusDao.deleteUserStatus()
    }
}