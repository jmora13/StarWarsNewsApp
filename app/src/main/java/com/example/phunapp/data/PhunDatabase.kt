package com.example.phunapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.phunapp.PhunModel.PhunModelItem

@Database(entities = [
    PhunModelItem::class
], version = 2, exportSchema = false)
abstract class PhunDatabase : RoomDatabase() {

    abstract fun phunDao() : PhunDao

    companion object{
        @Volatile
        private var INSTANCE: PhunDatabase? = null

        fun getDatabase(context: Context): PhunDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhunDatabase::class.java,
                    "phun_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}