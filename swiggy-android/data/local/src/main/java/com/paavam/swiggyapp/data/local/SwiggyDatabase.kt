package com.paavam.swiggyapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.paavam.swiggyapp.data.local.dao.CartDao
import com.paavam.swiggyapp.data.local.dao.UserAddressDao
import com.paavam.swiggyapp.data.local.entity.CartEntity
import com.paavam.swiggyapp.data.local.entity.UserAddressEntity

@Database(
    entities = [UserAddressEntity::class, CartEntity::class],
    version = 1
)
abstract class SwiggyDatabase : RoomDatabase() {

    abstract fun getFoodCartDao(): CartDao
    abstract fun getUserAddressDao(): UserAddressDao

    companion object {
        private const val DB_NAME = "swiggyapp_db"

        @Volatile
        private var INSTANCE: SwiggyDatabase? = null

        fun getInstance(context: Context): SwiggyDatabase {
            val cachedInstance = INSTANCE
            if (cachedInstance != null) {
                return cachedInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    SwiggyDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

