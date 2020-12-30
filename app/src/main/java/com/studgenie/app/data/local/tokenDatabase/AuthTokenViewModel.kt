package com.studgenie.app.data.local.tokenDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthTokenViewModel(application: Application) : AndroidViewModel(application) {
    var readAllData: LiveData<List<AuthTokenDataModel>>? = null
    private var tokenRepository: AuthTokenRepository? = null

    init {
        val authDao = AuthTokenDatabase.getDatabase(
            application
        ).getAuthDao()
        tokenRepository = AuthTokenRepository(authDao)
        readAllData = tokenRepository!!.readAllData
    }

    fun addAuthToken(authTokenDataModel: AuthTokenDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenRepository?.addAuthToken(authTokenDataModel)
        }
    }

    fun updateAuthToken(token: String, pid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenRepository?.updateAuthToken(token, pid)
        }
    }

    fun deleteAuthToken() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenRepository?.deleteAuthToken()
        }
    }
}