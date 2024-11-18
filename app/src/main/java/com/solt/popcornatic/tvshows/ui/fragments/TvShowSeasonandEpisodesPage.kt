package com.solt.popcornatic.tvshows.ui.fragments

import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable.ShaderFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.TvshowDetailPageSecondPartBinding
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.TvShowDetailResult
import com.solt.popcornatic.tvshows.ui.adapters.TvShowsSeasonAdapter
import com.solt.popcornatic.tvshows.ui.viewmodels.TvShowsDetailPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TvShowSeasonandEpisodesPage :Fragment() {
lateinit var binding:TvshowDetailPageSecondPartBinding
//Always pass the Id of the navgraph not the file location
val  viewModel :TvShowsDetailPageViewModel by hiltNavGraphViewModels<TvShowsDetailPageViewModel>(R.id.main_nav_graph)



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TvshowDetailPageSecondPartBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Initialize lists
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())

        }

        //Listen for the load operation events
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tvShowDetailsStateFlow.collectLatest {
               binding.bindState(it)

            }
        }

    }
    fun TvshowDetailPageSecondPartBinding.bindState(loadOperation: TvShowsDetailPageViewModel.LoadOperation){
        when(loadOperation){
            is TvShowsDetailPageViewModel.LoadOperation.Failure -> {}
            is TvShowsDetailPageViewModel.LoadOperation.Loading -> {

            }
            is TvShowsDetailPageViewModel.LoadOperation.Success<*> -> {
                val data = loadOperation.data as TvShowDetailResult

                Glide.with(posterImageCard).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${data.posterPath}").into(posterImageCard)
                Glide.with(backdropImageView).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${data.backdropPath}").fitCenter().into(backdropImageView)
                val seasonAdapter = TvShowsSeasonAdapter(data.id!!)
                recyclerView.adapter = seasonAdapter
                seasonAdapter.submitList(data.seasons?: emptyList())

            }
        }
    }

}