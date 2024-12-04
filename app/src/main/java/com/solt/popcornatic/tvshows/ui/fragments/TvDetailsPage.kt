package com.solt.popcornatic.tvshows.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Scene
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.MajorRootForTvShowsScenesBinding
import com.solt.popcornatic.databinding.TvShowDetailPageSecondSceneBinding
import com.solt.popcornatic.databinding.TvshowDetailPageFirstPartBinding
import com.solt.popcornatic.databinding.TvshowDetailPageSecondPartBinding
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.ProductionCompany
import com.solt.popcornatic.movies.ui.adapter.MovieDetailGenreListAdapter
import com.solt.popcornatic.movies.ui.adapter.MovieItemActions
import com.solt.popcornatic.movies.ui.adapter.ProductionCompaniesListAdapter
import com.solt.popcornatic.movies.ui.fragments.COMPANY_ID
import com.solt.popcornatic.movies.ui.fragments.CREDITS
import com.solt.popcornatic.movies.ui.fragments.IMAGES
import com.solt.popcornatic.movies.ui.fragments.TYPE
import com.solt.popcornatic.movies.ui.fragments.VIDEO
import com.solt.popcornatic.movies.ui.viewmodel.MovieDetailPageViewModel
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Genre
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.TvShowDetailResult
import com.solt.popcornatic.tvshows.ui.adapters.TvShowRecommendationsAdapter

import com.solt.popcornatic.tvshows.ui.viewmodels.TvShowsDetailPageViewModel
import com.solt.popcornatic.user.data.local.database.model.Type
import com.solt.popcornatic.user.ui.fragments.ITEMNAME
import com.solt.popcornatic.user.ui.fragments.ITEM_ID
import com.solt.popcornatic.user.ui.fragments.ITEM_POSTER_PATH
import com.solt.popcornatic.user.ui.fragments.ITEM_TYPE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val TV_SHOWS_ID = "tvShowId"
@AndroidEntryPoint
class TvDetailsPage: Fragment() {
    lateinit var rootBinding: MajorRootForTvShowsScenesBinding
    lateinit var firstSceneBinding :TvshowDetailPageFirstPartBinding
        lateinit var secondSceneBinding :TvShowDetailPageSecondSceneBinding
    var isOnSecondPartBinding = false
    val genreListAdapter = MovieDetailGenreListAdapter()
    val recommendedTvShowsAdapter = TvShowRecommendationsAdapter()
    val productionListOnClick = object : MovieItemActions{
        override fun onClick(view: View, movieId: Int) {
            val bundle = bundleOf(COMPANY_ID to movieId )
            findNavController().navigate(R.id.action_tvDetailsPage_to_tvshowProductionCompanyDetailsBottomDialog, bundle)
        }
    }
    val productionTvShowsAdapter = ProductionCompaniesListAdapter(productionListOnClick)
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
        secondSceneBinding = TvShowDetailPageSecondSceneBinding.inflate(inflater,container,false)
        if (isOnSecondPartBinding){
            rootBinding.rootSceneLayout.removeView(firstSceneBinding.root)
            rootBinding.rootSceneLayout.addView(secondSceneBinding.root)
        }
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


        secondSceneBinding.genreList.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            adapter = genreListAdapter
        }
        secondSceneBinding.recommendationsList.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            adapter = recommendedTvShowsAdapter
        }
        secondSceneBinding.productionCompaniesList.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            adapter = productionTvShowsAdapter
        }

        firstSceneBinding.seasonsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_tvDetailsPage_to_tvShowSeasonandEpisodesPage)
        }
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.tvShowDetailsStateFlow.collectLatest {

            rootBinding.bindState(it)
            firstSceneBinding.bindState(it)
            secondSceneBinding.bindState(it)
        }
        }

        val firstScene = Scene(rootBinding.rootSceneLayout,firstSceneBinding.root)
        val secondScene = Scene(rootBinding.rootSceneLayout,secondSceneBinding.root)

        val transition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.movie_detail_page_transition)
        val firstPartScrollOnTouchListener = object : View.OnTouchListener {
            var startY = 0.0f
            var endY = 0.0f
            val viewConfiguration = ViewConfiguration.get(requireContext())
            val touchSlop = viewConfiguration.scaledTouchSlop


            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return when(event?.action){
                    MotionEvent.ACTION_DOWN->{
                        startY =event.y
                        view.onTouchEvent(event)
                        true
                    }
                    MotionEvent.ACTION_UP->{
                        view.onTouchEvent(event)
                        true
                    }
                    MotionEvent.ACTION_MOVE->{
                        view.onTouchEvent(event)
                        endY = event.y
                        val deltaY = endY -startY
                        if (deltaY<touchSlop){
                            TransitionManager.go(secondScene, transition)
                            isOnSecondPartBinding = true
                        }

                        true
                    }

                    else -> {
                        view.onTouchEvent(event)
                        false
                    }
                }
            }

        }

        firstSceneBinding.root.setOnTouchListener(firstPartScrollOnTouchListener)


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

    fun TvshowDetailPageFirstPartBinding.bindState(loadState: TvShowsDetailPageViewModel.LoadOperation){

        when(loadState){


            is TvShowsDetailPageViewModel.LoadOperation.Failure -> {

            }
            is TvShowsDetailPageViewModel.LoadOperation.Loading -> {

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
                addToList.setOnClickListener {
                    val itemName = data.name
                    val itemId = data.id
                    val itemPoster = data.posterPath?:data.backdropPath
                    val itemType = Type.TV_SHOW

                    val bundle = bundleOf(
                        ITEMNAME to itemName, ITEM_TYPE to itemType.name, ITEM_ID to itemId,
                        ITEM_POSTER_PATH to itemPoster)
                    findNavController().navigate(R.id.action_tvDetailsPage_to_favouriteBottomDialog,bundle)
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
    fun TvShowDetailPageSecondSceneBinding.bindState(loadState: TvShowsDetailPageViewModel.LoadOperation) {
        when (loadState) {


            is TvShowsDetailPageViewModel.LoadOperation.Failure -> {

            }

            is TvShowsDetailPageViewModel.LoadOperation.Loading -> {

            }

            is TvShowsDetailPageViewModel.LoadOperation.Success<*> -> {
                val data = loadState.data as TvShowDetailResult
                apply {
                    apply {
                        popularityTv.text = (data.popularity?:0).toString()
                        voteRatingTv.text=(data.voteCount?:0).toString()
                        val voteAvg = Math.round(((data.voteAverage ?:0.0)*100)/10).toInt()
                        voteAvgTv.text ="$voteAvg%"
                        overviewTv.text = data.overview
                        originalTitleTv.text = data.originalName?:""
                        releaseDateBtn.text = data.firstAirDate?:"Not Yet"
                        releaseStatusBtn.text = data.status?:""
                        originalLanguageBtn.text = (data.originalLanguage?:"None") // Go to the configuration section of the api and store all languages
                        if(data.genres != null){
                            genreListAdapter.submitList(data.genres.map { com.solt.popcornatic.movies.data.model.MovieDetailPackage.Genre(it?.id,it?.name) })
                        }
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.getRecommendedTvShows(data.id!!).collectLatest {
                                recommendedTvShowsAdapter.submitData(it)
                            }
                        }
                        productionTvShowsAdapter.submitList(data.productionCompanies?.map { ProductionCompany(it?.id,it?.logoPath,it?.name,it?.originCountry) })


                        imagesSection.setOnClickListener {
                            val bundle = bundleOf(IMAGES to data.id, TYPE to Type.TV_SHOW.name)
                            findNavController().navigate(R.id.action_tvDetailsPage_to_movieImageListPage,bundle)
                        }
                        videosSection.setOnClickListener {
                            val bundle = bundleOf(VIDEO to data.id, TYPE to Type.TV_SHOW.name)
                            findNavController().navigate(R.id.action_tvDetailsPage_to_movieVideoListPage,bundle)
                        }
                        creditsSection.setOnClickListener {
                            val bundle = bundleOf(CREDITS to data.id, )
                            findNavController().navigate(R.id.action_tvDetailsPage_to_creditsPage,bundle)
                        }


                    }
            }
        }
    }
    }

}