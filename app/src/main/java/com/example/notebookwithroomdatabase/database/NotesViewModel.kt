package com.example.notebookwithroomdatabase.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NotesViewModel(application: Application):AndroidViewModel(application) {
    val repository:NotesRepository
    init {
        val dao=NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository=NotesRepository(dao)
    }

    fun addNotes(notesModelClass: NotesModelClass){
        repository.insertNotes(notesModelClass)
    }

    fun getNotes():LiveData<List<NotesModelClass>> = repository.getAllNotes()

    fun deleteNotes(id:Int){
        repository.deleteNotes(id)
    }

    fun updateNotes(notesModelClass: NotesModelClass){
        repository.updateNotes(notesModelClass)
    }
}