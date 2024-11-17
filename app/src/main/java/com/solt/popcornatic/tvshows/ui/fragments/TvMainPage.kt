package com.solt.popcornatic.tvshows.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.TvMainPageLayoutBinding
import com.solt.popcornatic.tvshows.ui.adapters.AiringTodayTvShowViewHolder
import com.solt.popcornatic.tvshows.ui.adapters.BaseTvShowViewHolder
import com.solt.popcornatic.tvshows.ui.adapters.TvShowItemActions
import com.solt.popcornatic.tvshows.ui.adapters.TvShowsListAdapter
import com.solt.popcornatic.tvshows.ui.viewmodels.TvShowsMainPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvMainPage: Fragment(),TvShowItemActions {
    lateinit var binding: TvMainPageLayoutBinding
    val airingTodayAdapter = TvShowsListAdapter(AiringTodayTvShowViewHolder::create,this)
    val onAirAdapter = TvShowsListAdapter(BaseTvShowViewHolder::create,this)
    val popularTvShowsAdapter = TvShowsListAdapter(BaseTvShowViewHolder::create,this)
    val topRatedTvShowsAdapter = TvShowsListAdapter(BaseTvShowViewHolder::create,this)
    val viewModel :TvShowsMainPageViewModel by viewModels<TvShowsMainPageViewModel> ()
    override fun onClick(view: View, tvShowId: Int) {
       val bundle = bundleOf(TV_SHOWS_ID to tvShowId)
        findNavController().navigate(R.id.action_tvMainPage_to_tvDetailsPage, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =TvMainPageLayoutBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Airing Today TvShows
        binding.airingTodayTvList.apply {
            adapter = airingTodayAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            PagerSnapHelper().attachToRecyclerView(this)

        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.listOfTvShowsAiringToday.collectLatest {
                    airingTodayAdapter.submitData(it)

                }
            }
        }
        //On the Air Tv Shows
        binding.onAirTvList.apply {
            adapter = onAirAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            // LinearSnapHelper().attachToRecyclerView(this)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.listOfTvShowsOnTheAir.collectLatest {
                    onAirAdapter.submitData(it)
                }
            }
        }

        //Popular TvShows
        binding.popularTvList.apply {
            adapter =popularTvShowsAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            // LinearSnapHelper().attachToRecyclerView(this)

        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.listOfPopularTvShows.collectLatest {
                    popularTvShowsAdapter.submitData(it)
                }
            }
        }

        //Top Rated Tv Shows
        binding.topRatedTvList.apply {
            adapter =topRatedTvShowsAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            //LinearSnapHelper().attachToRecyclerView(this)

        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.listOfTopRatedTvShows.collectLatest {
                    topRatedTvShowsAdapter.submitData(it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.bottomNavBar.setupWithNavController(findNavController())
    }

}