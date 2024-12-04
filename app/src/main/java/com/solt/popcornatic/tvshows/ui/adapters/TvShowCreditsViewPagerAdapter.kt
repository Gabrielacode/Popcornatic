package com.solt.popcornatic.tvshows.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Credits.Cast
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Credits.MovieDetailCredits
import com.solt.popcornatic.movies.ui.fragments.CastFragment
import com.solt.popcornatic.movies.ui.fragments.CrewFragment
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Credits.TvShowCreditsDetail
import com.solt.popcornatic.tvshows.ui.fragments.TvShowCastFragment
import com.solt.popcornatic.tvshows.ui.fragments.TvShowCrewFragment

class TvShowCreditsViewPagerAdapter(val homeFragment: Fragment, val data: TvShowCreditsDetail): FragmentStateAdapter(homeFragment){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return  when(position){
            0 -> TvShowCastFragment(data.cast?: emptyList())
            1-> TvShowCrewFragment(data.crew?: emptyList())
            else -> TvShowCastFragment(data.cast?: emptyList())
        }


    }


}