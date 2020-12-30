package com.studgenie.app.data.local.tokenDatabase

import androidx.lifecycle.LiveData

class AuthTokenRepository(private val authTokenDao: AuthTokenDao) {
    val readAllData: LiveData<List<AuthTokenDataModel>> = authTokenDao.getAuthToken()

    suspend fun addAuthToken(authTokenDataModel: AuthTokenDataModel) {
        authTokenDao.addAuthToken(authTokenDataModel)
    }

    suspend fun updateAuthToken(token: String, pid: Int) {
        authTokenDao.updateAuthToken(token, pid)
    }

    suspend fun deleteAuthToken() {
        authTokenDao.deleteAuthToken()
    }
}