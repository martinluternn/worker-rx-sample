package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.Const

@Database(
    version = MainDatabase.DATABASE_VERSION,
    exportSchema = true,
    entities = [
        DataCache::class
    ]
)

abstract class MainDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = Const.DATABASE_NAME
        const val DATABASE_VERSION = 1

        fun init(context: Context): MainDatabase {
            return Room.databaseBuilder(
                context,
                MainDatabase::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        }
    }

    abstract fun mainDao(): MainDao
}