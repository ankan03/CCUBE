package com.studgenie.app.data.local.tokenDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AuthTokenDao {
    @Insert
    suspend fun addAuthToken(authTokenDataModel: AuthTokenDataModel)

    @Query("UPDATE AuthTokenDataModel SET authToken=:token WHERE id=:pid")
    suspend fun updateAuthToken(token: String, pid: Int)

    @Query("SELECT * FROM AuthTokenDataModel ORDER BY id DESC")
    fun getAuthToken(): LiveData<List<AuthTokenDataModel>>

    @Query("DELETE FROM AuthTokenDataModel")
    suspend fun deleteAuthToken()
}