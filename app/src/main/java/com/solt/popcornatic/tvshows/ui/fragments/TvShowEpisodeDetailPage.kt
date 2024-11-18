package com.solt.popcornatic.tvshows.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.databinding.TvshowEpisodeDetailPageBinding
import com.solt.popcornatic.tvshows.data.remote.model.seasonsandepisodes.Season.Episode
import com.solt.popcornatic.tvshows.data.remote.repository.TvShowsRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TvShowEpisodeDetailPage:BottomSheetDialogFragment() {
    lateinit var binding:TvshowEpisodeDetailPageBinding
    @Inject
    lateinit var repository:TvShowsRepositoryImpl

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TvshowEpisodeDetailPageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvSeriesId = arguments?.getInt(TVSERIES_ID)
        val seasonNumber = arguments?.getInt(SEASON_NUMBER)
        val episodeNumber = arguments?.getInt(EPISODE_NUMBER)

        if (tvSeriesId == null || seasonNumber == null || episodeNumber == null) findNavController().popBackStack()

        viewLifecycleOwner.lifecycleScope.launch {
            val result = repository.getTvShowEpisode(tvSeriesId!!, seasonNumber!!,episodeNumber!!)
            when(result){
                is ApiResult.Failure.ApiFailure -> {}
                is ApiResult.Failure.NetworkFailure -> {}
                is ApiResult.Success<*> -> {
                    val data = result.data as Episode
                    binding.apply {
                        voteAvgTv.text = "${data.voteAverage?:0} %"
                        voteRatingTv.text = (data.voteCount?:0).toString()
                        originalTitleTv.text = data.name?:""
                        overviewTv.text = data.overview?:""
                        airedDateTv.text = data.airDate?:""

                    }
                }
            }
        }
    }

}