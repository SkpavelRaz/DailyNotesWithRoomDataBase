package com.example.notebookwithroomdatabase.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.notebookwithroomdatabase.R
import com.example.notebookwithroomdatabase.constance.NotesConstance
import com.example.notebookwithroomdatabase.database.NotesModelClass
import com.example.notebookwithroomdatabase.database.NotesViewModel
import com.example.notebookwithroomdatabase.databinding.FragmentCreateNotesBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateNotesFragment : Fragment() {

    private lateinit var binding: FragmentCreateNotesBinding
    private val viewModel: NotesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)

        Log.d("TAG", "onCreateView: ${arguments?.getString(NotesConstance.notesTypes)} ")
        when (arguments?.getString(NotesConstance.notesTypes)) {
            NotesConstance.updateNotes -> {
                binding.btnSave.text = getString(R.string.update)
                editeNotes(arguments?.getInt(NotesConstance.notesId))
            }
            else -> {
                binding.btnSave.text = getString(R.string.save)
            }
        }

        binding.btnSave.setOnClickListener {
            when (arguments?.getString(NotesConstance.notesTypes)) {
                arguments?.getString(NotesConstance.updateNotes) -> {

                }
                else -> {
                    setNotesData()
                }
            }
        }

        return binding.root
    }

    private fun editeNotes(id: Int?) {
        viewModel.getNotes().observe(viewLifecycleOwner) {
            binding.apply {
                if (id != null) {
                    etTitle.setText(it[id].title)
                    etSubTitle.setText(it[id].subTitle)
                    notes.setText(it[id].notes)
                }
            }
        }
    }


    private fun textFieldNotEmpty(): Boolean {
        binding.apply {
            return etTitle.text?.isEmpty() != true
        }
    }

    private fun setNotesData() {
        val title = binding.etTitle.text
        val subTitle = binding.etSubTitle.text
        val notes = binding.notes.text
        val currentDate = getTodayDate()

        if (textFieldNotEmpty()) {
            val notesData = NotesModelClass(
                null,
                title.toString(),
                subTitle.toString(),
                notes.toString(),
                currentDate,
                ""
            )
            viewModel.addNotes(notesData)
        } else {
            Toast.makeText(context, "Please FillUp Title for Save Your Notes", Toast.LENGTH_SHORT)
                .show()
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("dd LLL’ yy", Locale.getDefault())
        return dayFormat.format(calendar.time)
    }
}