package com.example.notebookwithroomdatabase.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
class NotesModelClass(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    var title:String?,
    var subTitle:String?,
    var notes:String?,
    var date:String?,
    var priority:String?
)