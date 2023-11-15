package com.txpdesenvolvimento.feelflow.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.databinding.FragmentEntryOverviewBinding
import com.txpdesenvolvimento.feelflow.utils.NavControllerSingleton

class EntryOverview : Fragment() {

    private var _binding : FragmentEntryOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEntryOverviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnNotes.setOnClickListener {
            NavControllerSingleton.getInstance().navigate(R.id.nav_entry_note)
        }

        return root
    }
}