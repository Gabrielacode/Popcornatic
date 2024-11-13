package com.solt.popcornatic.movies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.databinding.ListDialogLayoutBinding
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Videos.MovieDetailVideos
import com.solt.popcornatic.movies.data.repository.MovieRepositoryImpl
import com.solt.popcornatic.movies.ui.adapter.MovieVideoListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
 const val VIDEO ="video"
@AndroidEntryPoint
class MovieVideoListPage : Fragment() {
lateinit var binding:ListDialogLayoutBinding
@Inject
lateinit var repository: MovieRepositoryImpl


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListDialogLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getInt(VIDEO)
        if (movieId == null){
            findNavController().popBackStack()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            val videosResult = repository.getMovieVideosById(movieId!!)
            when(videosResult){
                is ApiResult.Failure.ApiFailure -> findNavController().popBackStack()
                is ApiResult.Failure.NetworkFailure -> findNavController().popBackStack()
                is ApiResult.Success<*> -> {
                    //We filter out only youtube videos to load we have not yet found a way to load other types of videos
                    val youtubeVideos = (videosResult.data as MovieDetailVideos).videoResults?.filter { it?.site?.contains("Youtube",true) == true }?: emptyList()
                     val videoAdapter = MovieVideoListAdapter(viewLifecycleOwner.lifecycle)
                    binding.root.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = videoAdapter
                        videoAdapter.submitList(youtubeVideos)
                    }
                }
            }
        }



    }

}