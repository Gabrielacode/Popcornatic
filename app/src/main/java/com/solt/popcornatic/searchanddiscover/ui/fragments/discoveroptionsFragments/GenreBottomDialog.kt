package com.solt.popcornatic.searchanddiscover.ui.fragments.discoveroptionsFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.DiscoverOptionsListDialogLayoutBinding
import com.solt.popcornatic.searchanddiscover.data.remote.models.DiscoverOptions.General.Genre.GenreOption
import com.solt.popcornatic.searchanddiscover.data.remote.repository.DiscoverOptionRepositoryImpl
import com.solt.popcornatic.searchanddiscover.ui.adapters.discoveroptions.GenreOptionsAdapter
import com.solt.popcornatic.searchanddiscover.ui.adapters.discoveroptions.GenreWrapper
import com.solt.popcornatic.searchanddiscover.ui.viewmodels.DiscoverOptions
import com.solt.popcornatic.searchanddiscover.ui.viewmodels.SearchDiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GenreBottomDialog:BottomSheetDialogFragment() {
    lateinit var binding:DiscoverOptionsListDialogLayoutBinding
    @Inject
    lateinit var repository: DiscoverOptionRepositoryImpl
    val viewModel by hiltNavGraphViewModels<SearchDiscoverViewModel>(R.id.main_nav_graph)
    val genreListAdapter= GenreOptionsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DiscoverOptionsListDialogLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = genreListAdapter
        }
        //Fetch the list of the genres
        viewLifecycleOwner.lifecycleScope.launch {
            val result =  repository.getListOfGenres()
            when(result){
                is ApiResult.Failure.ApiFailure -> {

                }
                is ApiResult.Failure.NetworkFailure -> {}
                is ApiResult.Success<*> -> {
                   val data = result.data as GenreOption
                    genreListAdapter.submitList(data.genres?.map { GenreWrapper(it,false) })
                }
            }
        }

        binding.submitButton.apply {
            setOnClickListener {
                //We get all the selected items in the list and add them as a option in the viewmodel
                viewModel.addMovieDiscoverOption(DiscoverOptions.Options.MovieOptions.GENRES,DiscoverOptions.MovieDiscoverOptions.ByGenres(genreListAdapter.currentList.filter { it.isChecked }.map { it.genre }))
                dismiss()
            }
        }
    }
}