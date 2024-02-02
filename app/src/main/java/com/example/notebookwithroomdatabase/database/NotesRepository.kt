package com.example.notebookwithroomdatabase.database

import androidx.lifecycle.LiveData

class NotesRepository(val dao:NotesDao) {
    fun getAllNotes():LiveData<List<NotesModelClass>>{
        return dao.getNotes()
    }
    fun insertNotes(notesModelClass: NotesModelClass){
        dao.insertNotes(notesModelClass)
    }
    fun deleteNotes(id:Int){
        dao.deleteNotes(id)
    }
    fun updateNotes(notesModelClass: NotesModelClass){
        dao.updateNotes(notesModelClass)
    }
}