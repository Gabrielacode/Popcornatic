package com.solt.popcornatic.user.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solt.popcornatic.databinding.DiscoverOptionsListDialogLayoutBinding
import com.solt.popcornatic.databinding.ListDialogLayoutBinding
import com.solt.popcornatic.user.data.local.database.model.Favourite
import com.solt.popcornatic.user.data.local.database.model.Item
import com.solt.popcornatic.user.data.local.database.model.Type
import com.solt.popcornatic.user.data.local.repository.FavouriteRepository
import com.solt.popcornatic.user.domain.FavouriteUseCase
import com.solt.popcornatic.user.domain.ItemUseCase
import com.solt.popcornatic.user.ui.adapters.AddFavouritesAdapter
import com.solt.popcornatic.user.ui.adapters.FavouritesWrapper
import com.solt.popcornatic.user.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

const val ITEMNAME = "item_name"
const val ITEM_ID = "item_id"
const val ITEM_POSTER_PATH = "item_poster_path"
const val ITEM_TYPE = "item_type"
@AndroidEntryPoint
class FavouriteBottomDialog:BottomSheetDialogFragment() {
    @Inject
     lateinit var favouriteUseCase: FavouriteUseCase
    @Inject
    lateinit var itemUseCase: ItemUseCase
    lateinit var binding: DiscoverOptionsListDialogLayoutBinding
    val favouritesAdapter = AddFavouritesAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DiscoverOptionsListDialogLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favouritesAdapter
        }
        //Get the list of favourites
        viewLifecycleOwner.lifecycleScope.launch {
         favouritesAdapter.submitList(favouriteUseCase.getAllFavourites().map { FavouritesWrapper(it,false) })
        }
        binding.submitButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val listOfSelectedFavourites = favouritesAdapter.currentList.filter { it.isChecked }
                val itemName = arguments?.getString(ITEMNAME)?:"None"
                val itemId = arguments?.getInt(ITEM_ID)
                val itemPosterPath = arguments?.getString(ITEM_POSTER_PATH)
                val itemType = arguments?.getString(ITEM_TYPE)
                if(itemType == null || itemId == null  ){
                    findNavController().popBackStack()
                }
                val item = Item(itemId!!,itemName,itemPosterPath!!, Type.valueOf(itemType!!))
                listOfSelectedFavourites.forEach {
                    itemUseCase.addItem(item, it.favourite)
                }
                dismiss()
            }


        }


    }

}