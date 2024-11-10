package com.solt.popcornatic.movies.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Scene
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.MajorRootForScenesBinding
import com.solt.popcornatic.databinding.MovieDetailPageFirstPartBinding
import com.solt.popcornatic.databinding.MovieDetailPageSecondPartBinding
import com.solt.popcornatic.movies.ui.adapter.BaseMovieViewHolder
import com.solt.popcornatic.movies.ui.adapter.MovieDetailGenreListAdapter
import com.solt.popcornatic.movies.ui.adapter.MovieDetailRecommendationsAdapter
import com.solt.popcornatic.movies.ui.adapter.MovieItemActions
import com.solt.popcornatic.movies.ui.adapter.MovieListAdapter
import com.solt.popcornatic.movies.ui.viewmodel.MovieDetailPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MovieDetailsPage:Fragment(),MovieItemActions {
    lateinit var majorRootForScenesBinding: MajorRootForScenesBinding
    lateinit var firstPartBinding: MovieDetailPageFirstPartBinding
    lateinit var secondPartBinding: MovieDetailPageSecondPartBinding
    val viewModel: MovieDetailPageViewModel by viewModels()
    var isOnSecondPartBinding = false
    val genreListAdapter = MovieDetailGenreListAdapter()
    val recommendationsAdapter = MovieDetailRecommendationsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            val movieId = arguments?.getInt(MOVIE_ID)
            if (movieId != null) {
                viewModel.getMovieDetails(movieId)
            } else findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        majorRootForScenesBinding = MajorRootForScenesBinding.inflate(inflater, container, false)

        firstPartBinding = majorRootForScenesBinding.scene
        secondPartBinding = MovieDetailPageSecondPartBinding.inflate(
            inflater,
            majorRootForScenesBinding.rootSceneLayout,
            false
        )
        if(isOnSecondPartBinding){
            majorRootForScenesBinding.rootSceneLayout.removeView(firstPartBinding.root)
            majorRootForScenesBinding.rootSceneLayout.addView(secondPartBinding.root,)
        }
        return majorRootForScenesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
          //Initialize Adapters
            secondPartBinding.genreList.apply {
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                adapter = genreListAdapter
            }
            secondPartBinding.recommendationsList.apply {
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                adapter = recommendationsAdapter
            }
            viewModel.movieDetailsStateFlow.collectLatest {
                bindState(it)
                firstPartBinding.bindLoadState(it)
                secondPartBinding.bindLocalState(it)
            }
        }
        val firstScene = Scene(majorRootForScenesBinding.rootSceneLayout, firstPartBinding.root)
        val secondScene = Scene(majorRootForScenesBinding.rootSceneLayout, secondPartBinding.root)
        val transition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.movie_detail_page_transition)

        firstPartBinding.toSecondPageBtn.setOnClickListener {
            TransitionManager.go(secondScene, transition)
            isOnSecondPartBinding = true
        }
//If in second Part if back is pressed go back to first part
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isOnSecondPartBinding) {
                        TransitionManager.go(firstScene, transition)


                        isOnSecondPartBinding = false
                    } else {
                        findNavController().navigateUp()
                    }
                }

            })


    }


    fun bindState(loadState: MovieDetailPageViewModel.LoadOperation) {
        when (loadState) {
            is MovieDetailPageViewModel.LoadOperation.Failure -> {
                majorRootForScenesBinding.titleTv.apply {
                    text = "Couldn't load title Network Error"

                }
            }

            is MovieDetailPageViewModel.LoadOperation.Loading -> {
                majorRootForScenesBinding.titleTv.apply {
                    text = "Loading"

                }
            }

            is MovieDetailPageViewModel.LoadOperation.Success<*> -> {
                majorRootForScenesBinding.titleTv.apply {
                    text = viewModel.movieDetails.title
                }

            }
        }
    }

    fun MovieDetailPageFirstPartBinding.bindLoadState(loadState: MovieDetailPageViewModel.LoadOperation) {

        when (loadState) {
            is MovieDetailPageViewModel.LoadOperation.Failure -> {}
            is MovieDetailPageViewModel.LoadOperation.Loading -> {}
            is MovieDetailPageViewModel.LoadOperation.Success<*> -> {
                this.posterImageView.apply {
                    Glide.with(this)
                        .load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${viewModel.movieDetails.posterPath}")
                        .placeholder(
                            R.drawable.ic_launcher_foreground
                        ).fitCenter().into(this)
                }
            }
        }
    }

    fun MovieDetailPageSecondPartBinding.bindLocalState(loadState: MovieDetailPageViewModel.LoadOperation) {
        when (loadState) {
            is MovieDetailPageViewModel.LoadOperation.Failure -> {}
            is MovieDetailPageViewModel.LoadOperation.Loading -> {}
            is MovieDetailPageViewModel.LoadOperation.Success<*> -> {
                apply {
                    popularityTv.text = (viewModel.movieDetails.popularity?:0).toString()
                    voteRatingTv.text=(viewModel.movieDetails.voteCount?:0).toString()
                    val voteAvg = Math.round(((viewModel.movieDetails.voteAverage ?:0.0)*100)/10).toInt()
                    voteAvgTv.text ="$voteAvg %"
                    overviewTv.text = viewModel.movieDetails.overview
                    originalTitleTv.text = viewModel.movieDetails.originalTitle?:"None"
                    releaseDateBtn.text = viewModel.movieDetails.releaseDate?:"Not Yet"
                    releaseStatusBtn.text = viewModel.movieDetails.status?:"None"
                    originalLanguageBtn.text = (viewModel.movieDetails.originalLanguage?:"None") // Go to the configuration section of the api and store all languages
                    //Use Bottom Dialog Fragment for OriginCountries
                    viewModel.movieDetails.originCountry
                    if(viewModel.movieDetails.genres != null){
                        genreListAdapter.submitList(viewModel.movieDetails.genres)
                    }
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.movieRecommendations.collect{
                            recommendationsAdapter.submitData(it)

                        }
                    }



                }
            }
        }
    }

    override fun onClick(view: View, movieId: Int) {
        //Fix transisition

        val bundle = bundleOf(MOVIE_ID to movieId)
        findNavController().navigate(R.id.action_movieDetailsPage_self,bundle,null)
    }
}
const val MOVIE_ID = "movie_id"
