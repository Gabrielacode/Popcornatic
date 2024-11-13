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
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Images.MovieDetailImages
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Images.toImage
import com.solt.popcornatic.movies.data.repository.MovieRepositoryImpl
import com.solt.popcornatic.movies.ui.adapter.MovieImageListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
const val MOVIEIMAGES = "movies_images"
@AndroidEntryPoint
class MovieImageListPage: Fragment() {
    lateinit var binding:ListDialogLayoutBinding
    @Inject
    lateinit var repository:MovieRepositoryImpl
    val imageAdapter = MovieImageListAdapter()

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
        val movieId = arguments?.getInt(MOVIEIMAGES)
        if (movieId== null){
            findNavController().popBackStack()
        }
        binding.root.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = imageAdapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            val result = repository.getMovieImagesById(movieId!!)
            when(result){
                is ApiResult.Failure.ApiFailure -> findNavController().popBackStack()
                is ApiResult.Failure.NetworkFailure -> findNavController().popBackStack()
                is ApiResult.Success<*> -> {
                    val data = result.data as MovieDetailImages
                    //Get the list of the poster , backdrop and logo images and map them to Image
                    //Merge them and maybe scatter them and submit them to the adapter

                    val listofPosters = data.posters?.map { it.toImage() }?: emptyList()
                    val listofBackDrops = data.backdrops?.map { it.toImage() }?: emptyList()
                    val listofLogos = data.logos?.map { it.toImage() }?: emptyList()
                    val fullList = (listofPosters+listofLogos+listofBackDrops).sortedBy { it.voteCount }

                    imageAdapter.submitList(fullList)


                }
            }

        }

    }

}