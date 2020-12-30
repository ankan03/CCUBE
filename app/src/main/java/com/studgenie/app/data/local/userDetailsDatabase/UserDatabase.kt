package com.studgenie.app.data.local.userDetailsDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserDataModel::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDataDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database1"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}