package com.example.notebookwithroomdatabase.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notebookwithroomdatabase.database.NotesModelClass
import com.example.notebookwithroomdatabase.databinding.NotesShowLayoutBinding
import com.example.notebookwithroomdatabase.utils.DiffCallback

class NotesDetailsAdapter(
    private val context: Context,
    private var notesList: List<NotesModelClass>,
    private val listener:OnCardClickListener
) :
    RecyclerView.Adapter<NotesDetailsAdapter.NotesViewHolder>() {
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<NotesModelClass>) {
        val diffCallback = DiffCallback(listOf(notesList), listOf(list))
        val diffResult = diffCallback.let { DiffUtil.calculateDiff(it) }
        notesList = list
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
    class NotesViewHolder(val binding:NotesShowLayoutBinding):RecyclerView.ViewHolder(binding.root)
    interface OnCardClickListener {
        fun cardClick(id:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = NotesShowLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return NotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (notesList.isEmpty()) 0 else notesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notes=notesList[position]
        holder.binding.apply {
            tvTitle.text=notes.title
            tvSubTitle.text=notes.subTitle
            tvNotes.text=notes.notes
            tvDate.text=notes.date
            root.setOnClickListener {
                notes.id?.let { id -> listener.cardClick(id) }
            }
        }

    }

}