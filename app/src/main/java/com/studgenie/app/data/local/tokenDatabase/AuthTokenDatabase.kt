package com.studgenie.app.data.local.tokenDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AuthTokenDataModel::class], version = 1, exportSchema = false)
abstract class AuthTokenDatabase : RoomDatabase() {
    abstract fun getAuthDao(): AuthTokenDao

    companion object {
        @Volatile
        private var INSTANCE: AuthTokenDatabase? = null
        fun getDatabase(context: Context): AuthTokenDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AuthTokenDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}