package com.studgenie.app.data.local.userStatusDatabase

//class UserStatusDatabase {
//}

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserStatusModel::class], version = 3, exportSchema = false)
abstract class UserStatusDatabase : RoomDatabase() {
    abstract fun getUserStatusDao(): UserStatusDao

    companion object {
        @Volatile
        private var INSTANCE: UserStatusDatabase? = null
        fun getUserStatusDatabase(context: Context): UserStatusDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserStatusDatabase::class.java,
                    "user_status"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}