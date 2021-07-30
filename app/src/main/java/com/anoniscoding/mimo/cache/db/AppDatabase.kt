package com.anoniscoding.mimo.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anoniscoding.mimo.cache.dao.LessonCompletionEventDao
import com.anoniscoding.mimo.cache.model.LessonCompletionEvent

@Database(entities = [LessonCompletionEvent::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lessonCompletionEventDao(): LessonCompletionEventDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, DB_NAME
                        )
                            .build()
                    }
                    return INSTANCE as AppDatabase
                }
            }
            return INSTANCE as AppDatabase
        }

        private const val DB_NAME = "mimo.db"
    }

}