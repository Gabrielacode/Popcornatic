package com.solt.popcornatic.movies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solt.popcornatic.databinding.CountryListItemBinding
import com.solt.popcornatic.databinding.CountryListLayoutBinding
import com.solt.popcornatic.movies.ui.adapter.CountryListAdapter

const val COUNTRY_LIST = "country_list"
class CountryListDialog :BottomSheetDialogFragment(){
     private var listOfOriginCountries :ArrayList<String?>? = null
    lateinit var binding: CountryListLayoutBinding
    val countryAdapter = CountryListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get the list of countries
        listOfOriginCountries = arguments?.getStringArrayList(COUNTRY_LIST)
        if(listOfOriginCountries == null){
            findNavController().popBackStack()
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CountryListLayoutBinding.inflate(inflater,container,false)
      return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.apply {
            adapter = countryAdapter
            countryAdapter.submitList(listOfOriginCountries)
        }
    }

}