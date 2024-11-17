package com.solt.popcornatic.movies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.MovieMainPageBinding
import com.solt.popcornatic.movies.ui.adapter.MovieListAdapter
import com.solt.popcornatic.movies.ui.adapter.BaseMovieViewHolder
import com.solt.popcornatic.movies.ui.adapter.MovieItemActions
import com.solt.popcornatic.movies.ui.adapter.TrendingMovieViewHolder
import com.solt.popcornatic.movies.ui.viewmodel.MovieMainPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieMainPage:Fragment(), MovieItemActions {
    lateinit var binding: MovieMainPageBinding
    val trendingMovieAdapter = MovieListAdapter(TrendingMovieViewHolder::create,this)
    val popularMovieAdapter = MovieListAdapter(BaseMovieViewHolder::create,this)
    val upcomingMovieAdapter = MovieListAdapter(BaseMovieViewHolder::create,this)
    val topRatedMovieAdapter = MovieListAdapter(BaseMovieViewHolder::create,this)
    val viewModel by viewModels<MovieMainPageViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieMainPageBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Trending Movies
        binding.trendingMoviesList.apply {
            adapter = trendingMovieAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            PagerSnapHelper().attachToRecyclerView(this)

        }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.listOfTrendingMovies.collectLatest {
                        trendingMovieAdapter.submitData(it)

                    }
                }
            }
       //Popular Movies
        binding.popularMoviesList.apply {
            adapter = popularMovieAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
           // LinearSnapHelper().attachToRecyclerView(this)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.listOfPopularMovies.collectLatest {
                    popularMovieAdapter.submitData(it)
                }
            }
        }

        //Upcoming Movies
        binding.upcomingMoviesList.apply {
            adapter =upcomingMovieAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
           // LinearSnapHelper().attachToRecyclerView(this)

        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.listOfUpcomingMovies.collectLatest {
                    upcomingMovieAdapter.submitData(it)
                }
            }
        }

        //Top Rated Movies
        binding.topratedMovieList.apply {
            adapter =topRatedMovieAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            //LinearSnapHelper().attachToRecyclerView(this)

        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.listOfTopRatedMovies.collectLatest {
                    topRatedMovieAdapter.submitData(it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.bottomNavBar.setupWithNavController(findNavController())
    }



    override fun onClick( view: View,movieId: Int) {
        // Fix Transition

     val bundle = bundleOf(MOVIE_ID to movieId)
     findNavController().navigate(R.id.action_movieMainPage_to_movieDetailsPage,bundle,null)
    }
}
//A function for lauching flows in a lifecycle scope
  suspend fun <T> Flow<T>. launchOnLifecycleScope(lifecycleOwner: LifecycleOwner,repeatState:Lifecycle.State, action: (T) -> Unit) {
         lifecycleOwner.repeatOnLifecycle(repeatState){
             this@launchOnLifecycleScope.collectLatest{
                 action(it)
             }
         }

 }
