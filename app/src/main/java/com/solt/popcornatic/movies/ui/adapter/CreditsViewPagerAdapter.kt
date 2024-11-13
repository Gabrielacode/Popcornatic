package com.solt.popcornatic.movies.ui.adapter


import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Credits.Cast
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Credits.MovieDetailCredits
import com.solt.popcornatic.movies.ui.fragments.CastFragment
import com.solt.popcornatic.movies.ui.fragments.CrewFragment
import com.solt.popcornatic.movies.ui.fragments.FragmentType
import com.solt.popcornatic.movies.ui.fragments.TYPE

class CreditsViewPagerAdapter(val homeFragment:Fragment ,val data:MovieDetailCredits): FragmentStateAdapter(homeFragment){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
         return  when(position){
             0 -> CastFragment(data.cast?: emptyList<Cast>())
             1->CrewFragment(data.crew?: emptyList())
             else -> CastFragment(data.cast?: emptyList<Cast>())
         }


    }


}