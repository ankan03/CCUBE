package com.studgenie.app.data.local.userDetailsDatabase

import androidx.lifecycle.LiveData


class UserRepository(private val userDao: UserDao) {
    val readAllDataModel: LiveData<List<UserDataModel>> = userDao.getUserData()

    suspend fun addUserData(userDataModel: UserDataModel) {
        userDao.addUserData(userDataModel)
    }

    suspend fun updateUserData(number: String, username: String, email: String, pid: Int) {
        userDao.updateUserData(number, username, email, pid)
    }

    suspend fun deleteUserData() {
        userDao.deleteUserData()
    }

}