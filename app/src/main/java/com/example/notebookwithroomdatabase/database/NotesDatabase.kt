package com.example.notebookwithroomdatabase.database

import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
abstract class NotesDatabase :RoomDatabase(){
    abstract fun myNotesDao():NotesDao

    companion object {
        @Volatile
        var INSTANCE:NotesDatabase?=null

        fun getDatabaseInstance(context: Context):NotesDatabase{

            val tempInstance= INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this)
            {
                val roomDatabaseInstance=Room.databaseBuilder(context,NotesDatabase::class.java,"Notes").build()
                INSTANCE=roomDatabaseInstance
                return roomDatabaseInstance
            }
        }
    }
}