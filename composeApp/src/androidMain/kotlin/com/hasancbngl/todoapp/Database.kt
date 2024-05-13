package com.hasancbngl.todoapp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import data.TaskDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<TaskDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("tasks.db")
    return Room.databaseBuilder<TaskDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}