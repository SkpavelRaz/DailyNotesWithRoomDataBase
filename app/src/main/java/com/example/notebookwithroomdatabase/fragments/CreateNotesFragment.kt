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
import androidx.navigation.fragment.findNavController
import com.example.notebookwithroomdatabase.R
import com.example.notebookwithroomdatabase.constance.NotesConstance
import com.example.notebookwithroomdatabase.database.NotesModelClass
import com.example.notebookwithroomdatabase.database.NotesViewModel
import com.example.notebookwithroomdatabase.databinding.FragmentCreateNotesBinding
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateNotesFragment : Fragment() {

    private lateinit var binding: FragmentCreateNotesBinding
    private val viewModel: NotesViewModel by viewModels()
    private var notesId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)

        when (arguments?.getString(NotesConstance.notesTypes)) {
            NotesConstance.updateNotes -> {
                binding.btnSave.text = getString(R.string.update)
                setEditeNotes(arguments?.getInt(NotesConstance.notesId))
            }

            else -> {
                binding.btnSave.text = getString(R.string.save)
            }
        }

        binding.btnSave.setOnClickListener {
            setNotesData()
        }
        binding.btnCancel.setOnClickListener {
            goBack()
        }

        return binding.root
    }

    private fun setEditeNotes(id: Int?) {
        viewModel.getNotes().observe(viewLifecycleOwner) {
            binding.apply {
                if (id != null) {
                    for (i in it.indices) {
                        if (it[i].id == id) {
                            etTitle.setText(it[i].title)
                            etSubTitle.setText(it[i].subTitle)
                            notes.setText(it[i].notes)
                            notesId = it[i].id
                        }
                    }
                }
            }
        }
    }


    private fun setNotesData() {
        val title = binding.etTitle.text
        val subTitle = binding.etSubTitle.text
        val notes = binding.notes.text
        val currentDate = getTodayDate()

        if (textFieldNotEmpty()) {
            when (arguments?.getString(NotesConstance.notesTypes)) {
                NotesConstance.newNotes -> {
                    //add new notes
                    val notesData = NotesModelClass(
                        0,
                        title.toString(),
                        subTitle.toString(),
                        notes.toString(),
                        currentDate,
                        ""
                    )
                    viewModel.addNotes(notesData)
                    Log.d("TAG", "setNotesData: ${Gson().toJson(notesData)}")
                }

                else -> {
                    //edite old news and update
                    val notesUpdateData = NotesModelClass(
                        notesId,
                        title.toString(),
                        subTitle.toString(),
                        notes.toString(),
                        currentDate,
                        ""
                    )
                    viewModel.updateNotes(notesUpdateData)
                    Log.d("TAG", "notesUpdateData: ${Gson().toJson(notesUpdateData)}")

                }
            }
            goBack()

        } else {
            Toast.makeText(context, "Please FillUp Title for Save Your Notes", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun goBack() {
        if (parentFragmentManager.backStackEntryCount == 0) activity?.finish()
        else findNavController().popBackStack()
    }

    private fun textFieldNotEmpty(): Boolean {
        binding.apply {
            return etTitle.text?.isEmpty() != true
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("dd LLL’ yy", Locale.getDefault())
        return dayFormat.format(calendar.time)
    }
}