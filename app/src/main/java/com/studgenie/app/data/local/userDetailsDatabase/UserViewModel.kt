package com.studgenie.app.data.local.userDetailsDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class UserViewModel {
//}

class UserViewModel(application: Application) : AndroidViewModel(application) {
    var readAllDataModel: LiveData<List<UserDataModel>>? = null
    private var repository: UserRepository? = null

    init {
        val userDao = UserDatabase.getDatabase(
            application
        ).getUserDataDao()
        repository = UserRepository(userDao)
        readAllDataModel = repository!!.readAllDataModel
    }

    fun addUserData(userDataModel: UserDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository?.addUserData(userDataModel)
        }
    }

    fun updateUserData(number: String, username: String, email: String, pid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository?.updateUserData(number, username, email, pid)
        }
    }

    fun deleteUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository?.deleteUserData()
        }
    }
}