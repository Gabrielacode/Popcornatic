package com.solt.popcornatic.movies.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.solt.popcornatic.databinding.ListDialogLayoutBinding
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Credits.Cast
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Credits.Crew
import com.solt.popcornatic.movies.ui.adapter.CastListAdapter
import com.solt.popcornatic.movies.ui.adapter.CrewListAdapter

enum class FragmentType(val value:String){
    CAST("cast"),CREW("crew")
}
const val CAST_OR_CREW_KEY = "cast_or_crew"
const val TYPE = "type"
class CastFragment(val listofCast: List<Cast>) :Fragment(){
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
class CrewFragment( val listofCrew:List<Crew>):Fragment(){
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