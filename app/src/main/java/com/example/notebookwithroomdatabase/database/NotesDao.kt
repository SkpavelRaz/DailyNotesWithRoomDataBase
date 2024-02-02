package com.example.notebookwithroomdatabase.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {
    @Query("SELECT * FROM Notes")
    fun getNotes():LiveData<List<NotesModelClass>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes:NotesModelClass)

    @Query("DELETE FROM NOTES WHERE id=:id")
    fun deleteNotes(id:Int)

    @Update
    fun updateNotes(notes: NotesModelClass)

}