package com.example.notebookwithroomdatabase.fragments

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notebookwithroomdatabase.R
import com.example.notebookwithroomdatabase.adapter.NotesDetailsAdapter
import com.example.notebookwithroomdatabase.constance.NotesConstance
import com.example.notebookwithroomdatabase.database.NotesModelClass
import com.example.notebookwithroomdatabase.database.NotesViewModel
import com.example.notebookwithroomdatabase.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var notesAdapter: NotesDetailsAdapter
    private lateinit var notesData:List<NotesModelClass>
    private val viewModel: NotesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.floatAddButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createNotesFragment,
                bundleOf(NotesConstance.notesTypes to NotesConstance.newNotes)
            )
        }
        notesData= mutableListOf()
        setAdapter()
        callViewModel()

        binding.etSearchBox.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty() == true){
                    searchResult(s.toString())
                }else{
                    notesAdapter.updateList(notesData)
                }
            }

        })
        return binding.root
    }

    private fun searchResult(key: String) {
        val searchList=notesData.filter {
            it.title?.contains(key,true)?:false ||
            it.subTitle?.contains(key,true)?:false ||
            it.notes?.contains(key,true)?:false ||
            it.date?.contains(key,true)?:false
        }

        notesAdapter.updateList(searchList)
    }

    private fun callViewModel() {
        viewModel.getNotes().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                notesData = it
                notesAdapter.updateList(it)
                Log.d("TAG", "cardClick: ${Gson().toJson(notesData)} ")

            }
        }
    }

    private fun setAdapter() {
        notesAdapter=NotesDetailsAdapter(requireContext(),notesData,object :NotesDetailsAdapter.OnCardClickListener{
            override fun cardClick(note: NotesModelClass) {
                findNavController().navigate(R.id.action_homeFragment_to_createNotesFragment,
                    bundleOf(
                        NotesConstance.notesTypes to NotesConstance.updateNotes,
                        NotesConstance.notesId to note.id
                    )
                )

            }

            override fun cardDeleteClick(note: NotesModelClass) {
                viewModel.deleteNotes(note.id)
                callViewModel()
            }

        })

        binding.rvAllNotes.apply {
            setHasFixedSize(true)
            adapter=notesAdapter
        }
    }
}