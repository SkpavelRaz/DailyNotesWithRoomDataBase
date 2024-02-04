package com.example.notebookwithroomdatabase.fragments

import android.app.Application
import android.content.Context
import android.os.Bundle
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
        return binding.root
    }

    private fun callViewModel() {
        viewModel.getNotes().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                notesData = it
                notesAdapter.updateList(it)

            }
        }
    }

    private fun setAdapter() {
        notesAdapter=NotesDetailsAdapter(requireContext(),notesData,object :NotesDetailsAdapter.OnCardClickListener{
            override fun cardClick(id: Int) {
                findNavController().navigate(R.id.action_homeFragment_to_createNotesFragment,
                    bundleOf(
                        NotesConstance.notesTypes to NotesConstance.updateNotes,
                        NotesConstance.notesId to id
                    )
                )
            }

        })

        binding.rvAllNotes.apply {
            setHasFixedSize(true)
            adapter=notesAdapter
        }
    }
}