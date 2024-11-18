package com.solt.popcornatic.tvshows.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.databinding.TvshowSeasonDetailPageBinding
import com.solt.popcornatic.databinding.TvshowSeasonItemBinding
import com.solt.popcornatic.tvshows.data.remote.model.seasonsandepisodes.Season.TvShowSeasonDetail
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.TvShowDetailResult
import com.solt.popcornatic.tvshows.data.remote.repository.TvShowsRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TVSERIES_ID = "tvseriesId"
const val SEASON_NUMBER = "seasonNumber"
const val EPISODE_NUMBER = "episodeNumber"

@AndroidEntryPoint
class TvShowSeasonDetailPage: BottomSheetDialogFragment() {
    lateinit var binding:TvshowSeasonDetailPageBinding
    @Inject
   lateinit var repository:TvShowsRepositoryImpl


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TvshowSeasonDetailPageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Get the tv series Id and season Number


       val tvSeriesNumber = arguments?.getInt(TVSERIES_ID)
       val  seasonNumber = arguments?.getInt(SEASON_NUMBER)

        //Check if null pop to back stack
        if (tvSeriesNumber == null || seasonNumber == null) findNavController().popBackStack()

            //Load season detail
         viewLifecycleOwner.lifecycleScope.launch {
             val result =  repository.getTvShowSeason(tvSeriesNumber!!,seasonNumber!!)
             when(result){
                 is ApiResult.Failure.ApiFailure -> {
                 }
                 is ApiResult.Failure.NetworkFailure -> {

                 }
                 is ApiResult.Success<*> -> {
                     val data = result.data as TvShowSeasonDetail
                     binding.apply {
                         voteAvgTv.text = "${data.voteAverage?:0.0} %"
                         originalTitleTv.text = data.name?:"No Name"
                         overviewTv.text = data.overview?:" No Overview"
                         startedAiringTv.text = data.airDate?:"Not yet"

                     }
                 }
             }
        }

    }



}