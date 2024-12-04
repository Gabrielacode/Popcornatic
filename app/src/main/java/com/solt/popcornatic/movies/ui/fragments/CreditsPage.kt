package com.solt.popcornatic.movies.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.databinding.CreditsLayoutBinding
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Credits.MovieDetailCredits
import com.solt.popcornatic.movies.data.repository.MovieRepositoryImpl
import com.solt.popcornatic.movies.ui.adapter.CreditsViewPagerAdapter
import com.solt.popcornatic.tvshows.data.remote.repository.TvShowsRepositoryImpl
import com.solt.popcornatic.user.data.local.database.model.Type
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

const val  CREDITS = "credits"
@AndroidEntryPoint
class CreditsPage : Fragment(){
    lateinit var binding: CreditsLayoutBinding
    @Inject lateinit var movieRepo:MovieRepositoryImpl

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreditsLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Get the movie Id
        val id = arguments?.getInt(CREDITS)
        if(id==null){
            findNavController().popBackStack()

        }
        viewLifecycleOwner.lifecycleScope.launch {
            val result =
                movieRepo.getMovieCreditsById(id!!)

            when(result){
                is ApiResult.Failure.ApiFailure -> {
                    findNavController().popBackStack()
                }
                is ApiResult.Failure.NetworkFailure -> {
                    findNavController().popBackStack()
                }
                is ApiResult.Success<*> -> {
                    val data = result.data as MovieDetailCredits
                    binding.also {
                        it.viewpager.adapter = CreditsViewPagerAdapter(this@CreditsPage,data)
                        TabLayoutMediator(it.tabLayout,it.viewpager){
                            tab,position->
                                when (position) {
                                    0 -> tab.text = "Cast"
                                    1 -> tab.text = "Crew"
                                }

                        }.attach()
                    }


                }
            }
        }

    }

}