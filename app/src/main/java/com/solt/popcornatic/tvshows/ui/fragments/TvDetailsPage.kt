package com.solt.popcornatic.tvshows.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.MajorRootForTvShowsScenesBinding
import com.solt.popcornatic.databinding.TvshowDetailPageFirstPartBinding
import com.solt.popcornatic.databinding.TvshowDetailPageSecondPartBinding
import com.solt.popcornatic.movies.ui.viewmodel.MovieDetailPageViewModel
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.TvShowDetailResult

import com.solt.popcornatic.tvshows.ui.viewmodels.TvShowsDetailPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val TV_SHOWS_ID = "tvShowId"
@AndroidEntryPoint
class TvDetailsPage: Fragment() {
    lateinit var rootBinding: MajorRootForTvShowsScenesBinding
    lateinit var firstSceneBinding :TvshowDetailPageFirstPartBinding
    //We will save the tv show id in the fragment since the viewmodel is scoped to the nav graph

    var tvShowId:Int? = null
 val viewModel:TvShowsDetailPageViewModel   by hiltNavGraphViewModels<TvShowsDetailPageViewModel>(R.id.main_nav_graph)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tvShowId = arguments?.getInt(TV_SHOWS_ID)
        if (tvShowId!= null){
           this.tvShowId = tvShowId
        }else findNavController().popBackStack()

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootBinding = MajorRootForTvShowsScenesBinding.inflate(inflater,container,false)
        firstSceneBinding = rootBinding.scene
        return rootBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(this.tvShowId != viewModel.tvShowId){
            if(this.tvShowId == null){
                findNavController().popBackStack()
            }else{
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getTvShow(tvShowId!!)
                }

            }

        }

        firstSceneBinding.seasonsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_tvDetailsPage_to_tvShowSeasonandEpisodesPage)
        }
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.tvShowDetailsStateFlow.collectLatest {

            rootBinding.bindState(it)
            firstSceneBinding.bindState(it)
        }
        }


    }

    fun TvshowDetailPageFirstPartBinding.bindState(loadState: TvShowsDetailPageViewModel.LoadOperation){

        when(loadState){


            is TvShowsDetailPageViewModel.LoadOperation.Failure -> {
                rootBinding.titleTv.text = "Network Error"
            }
            is TvShowsDetailPageViewModel.LoadOperation.Loading -> {
                rootBinding.titleTv.text = "Loading"
            }
            is TvShowsDetailPageViewModel.LoadOperation.Success<*> ->{
                val data = loadState.data as TvShowDetailResult
                posterImageView.apply {
                    Glide.with(this)
                        .load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${data.posterPath}")
                        .placeholder(
                            R.drawable.ic_launcher_foreground
                        ).fitCenter().into(this)
                }
            }
        }
    }
    fun MajorRootForTvShowsScenesBinding.bindState(loadState: TvShowsDetailPageViewModel.LoadOperation){
        when(loadState){
            is TvShowsDetailPageViewModel.LoadOperation.Failure ->
            {
                titleTv.text ="Network Error"
            }
            is TvShowsDetailPageViewModel.LoadOperation.Loading -> {
                titleTv.text ="Loading"
            }
            is TvShowsDetailPageViewModel.LoadOperation.Success<*> -> {
               val data = loadState.data as TvShowDetailResult
                titleTv.text =data.name
            }
        }
    }


}