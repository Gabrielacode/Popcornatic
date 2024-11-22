package com.solt.popcornatic.searchanddiscover.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.DiscoverSceneBinding
import com.solt.popcornatic.databinding.SearchAndDiscoverPageBinding
import com.solt.popcornatic.databinding.SearchSceneBinding
import com.solt.popcornatic.searchanddiscover.data.remote.models.SearchItem
import com.solt.popcornatic.searchanddiscover.data.remote.paging.SearchResultsType
import com.solt.popcornatic.searchanddiscover.ui.adapters.SearchItemAdapter
import com.solt.popcornatic.searchanddiscover.ui.viewmodels.DiscoverOptions
import com.solt.popcornatic.searchanddiscover.ui.viewmodels.SearchDiscoverViewModel
import com.solt.popcornatic.searchanddiscover.ui.viewmodels.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchAndDiscoverPage: Fragment() {
    lateinit var binding:SearchAndDiscoverPageBinding
    val queryStateFlow = MutableStateFlow("")
    val viewModel :SearchDiscoverViewModel by hiltNavGraphViewModels<SearchDiscoverViewModel> (R.id.main_nav_graph)
    val searchAdapter = SearchItemAdapter()
    lateinit var firstSceneBinding: SearchSceneBinding
    lateinit var secondSceneBinding: DiscoverSceneBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchAndDiscoverPageBinding.inflate(inflater,container,false)
        firstSceneBinding = binding.scene
        secondSceneBinding = DiscoverSceneBinding.inflate(inflater,binding.rootScene,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchScene = androidx.transition.Scene(binding.rootScene,firstSceneBinding.root)
        val discoverScene = androidx.transition.Scene(binding.rootScene,secondSceneBinding.root)
        binding.searchResult.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
        //Listen for state changes in the viewmodel
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateFlow.collectLatest {
                when(it){
                    State.DISCOVER -> {
                        TransitionManager.go(discoverScene)
                        //If nothing is selected automatically select the movie
                        if(binding.materialButtonToggleGroup.checkedButtonIds.isEmpty()){
                            binding.materialButtonToggleGroup.check(R.id.movie_select_button)
                        }

                    }
                    State.SEARCH ->  {TransitionManager.go(searchScene)}
                }
            }
        }
            //Listen for changes in the movie list
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieOptionsStateFlow.collectLatest {
               secondSceneBinding.discoverOptionsChipGroup.setMovieOptions(it)
                viewModel.discover(it).collectLatest { list:PagingData<SearchItem> ->
                    launch{
                        searchAdapter.submitData(list)
                    }

                }

            }
        }
        //Check the material toggle group if one button is checked then set the search result to the specified type
        binding.materialButtonToggleGroup.addOnButtonCheckedListener { materialButtonToggleGroup, i, b ->
           when{
               materialButtonToggleGroup.checkedButtonIds.containsAll(listOf(R.id.movie_select_button,R.id.tv_select_button))->{
                   viewModel.searchResultsType = SearchResultsType.BOTH
               }
               materialButtonToggleGroup.checkedButtonIds.contains(R.id.movie_select_button)-> viewModel.searchResultsType = SearchResultsType.MOVIES_ONLY
               materialButtonToggleGroup.checkedButtonIds.contains(R.id.tv_select_button)-> viewModel.searchResultsType = SearchResultsType.TVSHOWS_ONLY
               else -> {
                   if(viewModel.state == State.DISCOVER){
                       materialButtonToggleGroup.check(R.id.movie_select_button)
                   }else{viewModel.searchResultsType = SearchResultsType.BOTH}}

           }
        }

        //The set state button will change the state to either discover or search
        binding.discoverToSearch.setOnClickListener {
            viewModel.setState()
        }



       // Collect the query and search for them
        viewLifecycleOwner.lifecycleScope.launch {
            queryStateFlow.collectLatest { query ->
                viewModel.search(query).collectLatest {
                    searchAdapter.submitData(it)
                }
            }
        }

        firstSceneBinding.searchEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                queryStateFlow.value = query
            }

        })

        //Discover
        secondSceneBinding.addOption.setOnClickListener {
              findNavController().navigate(R.id.action_searchAndDiscoverPage_to_optionSelectorDialog)
        }

        //The chip group will remove all and add the group

        }

    private fun ChipGroup.setMovieOptions( options:Map<DiscoverOptions.Options.MovieOptions, DiscoverOptions.MovieDiscoverOptions>){
        //First Remove all the  chips
        //Animate it maybe
        if(this.childCount>0){
            this.removeAllViews()
        }
        options.forEach { t, u ->
            //For each option add the appropraite options
            addView(Chip(this.context).apply {
                text = u.toDisplay()

                setOnLongClickListener { viewModel.removeMovieDiscoverOption(t)
                    true}

            })
        }

    }

    }
