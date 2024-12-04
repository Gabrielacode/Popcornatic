package com.solt.popcornatic.tvshows.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.solt.popcornatic.databinding.ListDialogLayoutBinding
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Credits.Cast
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Credits.Crew
import com.solt.popcornatic.tvshows.ui.adapters.CastListAdapter
import com.solt.popcornatic.tvshows.ui.adapters.CrewListAdapter


class TvShowCastFragment(val listofCast: List<Cast>) : Fragment(){
    lateinit var binding: ListDialogLayoutBinding
    val listAdapter = CastListAdapter()
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
        Log.i("Hoise","Cast or Crew")
        binding.root.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
            listAdapter.submitList(listofCast)
        }

    }
}
class TvShowCrewFragment( val listofCrew:List<Crew>):Fragment(){
    lateinit var binding: ListDialogLayoutBinding
    val listAdapter = CrewListAdapter()

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
        binding.root.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
            listAdapter.submitList(listofCrew)
        }
    }

}