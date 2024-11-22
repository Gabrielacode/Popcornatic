package com.solt.popcornatic.searchanddiscover.ui.fragments.discoveroptionsFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.ListDialogLayoutBinding
import com.solt.popcornatic.searchanddiscover.ui.adapters.discoveroptions.MovieOptionsSelectorAdapter
import com.solt.popcornatic.searchanddiscover.ui.viewmodels.DiscoverOptions

//This will display the options that can be added
class OptionSelectorDialog:BottomSheetDialogFragment() {
    lateinit var binding:ListDialogLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListDialogLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MovieOptionsSelectorAdapter(::click)
        }
    }
    fun click(option:DiscoverOptions.Options.MovieOptions,view:View){
        when(option){
            DiscoverOptions.Options.MovieOptions.GENRES -> {
                findNavController().navigate(R.id.action_optionSelectorDialog_to_genreBottomDialog)
            }
        }
    }
}