package com.solt.popcornatic.user.ui.fragments

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.Companion.isPhotoPickerAvailable
import androidx.appcompat.widget.ActivityChooserView
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.solt.popcornatic.R
import com.solt.popcornatic.user.ui.viewmodel.UserViewModel
import com.solt.popcornatic.databinding.UserPageLayoutBinding
import com.solt.popcornatic.user.ui.adapters.FavouriteListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserPage:Fragment()
{
    lateinit var binding: UserPageLayoutBinding
    val viewModel :UserViewModel by viewModels<UserViewModel>()
    var isUserSettingName= false
    val favouriteListAdapter = FavouriteListAdapter()
   lateinit var pickPhotoActivityLauncher:ActivityResultLauncher<PickVisualMediaRequest>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       pickPhotoActivityLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null){
                val inputStream = requireContext().contentResolver.openInputStream(it)
                if (inputStream != null) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val isImageWritten =   viewModel.setUserImage(inputStream)
                        if(isImageWritten){
                            val bitmap = viewModel.getUserImage()
                            binding.shapeableImageView.setImageBitmap(bitmap)
                        }
                    }
                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= UserPageLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //On Long Click  the user name becomes the edit text for the user to be able to set his or her name
        binding.userName.apply {
              setText(viewModel.getUserName())
            setOnLongClickListener {
                if(!isUserSettingName){
                    isUserSettingName = true
                    inputType =  EditorInfo.TYPE_CLASS_TEXT
                    binding.submitButton.visibility = View.VISIBLE
                }
                true
            }
        }
        binding.submitButton.apply {
            setOnClickListener {
                val text = binding.userName.text
                if(text.isBlank()){
                    binding.userName.error = "Text Empty"
                    return@setOnClickListener
                }else {
                    viewModel.setUserName(text.toString())
                    binding.userName.inputType =  EditorInfo.TYPE_NULL
                    binding.submitButton.visibility = View.GONE
                    isUserSettingName = false
                }


            }
        }
        binding.addFavourite.setOnClickListener {
            findNavController().navigate(R.id.action_userPage_to_addFavouritePage)
        }
        binding.favouriteList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favouriteListAdapter
        }

        //Collect Flow of Paging Source
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getListOfFavourites().collectLatest {
                favouriteListAdapter.submitData(it)
            }
        }

      viewLifecycleOwner.lifecycleScope.launch {
           val bitmap = viewModel.getUserImage()
           binding.shapeableImageView.setImageBitmap(bitmap)
      }
        binding.shapeableImageView.setOnClickListener {
         chooseUserImage(pickPhotoActivityLauncher)
        }
        val tmdbWebsiteIntentBuilder = CustomTabsIntent.Builder().setDefaultColorSchemeParams(CustomTabColorSchemeParams.Builder().setToolbarColor(resources.getColor(R.color.main_yellow,requireActivity().theme)).setNavigationBarColor(resources.getColor(R.color.main_surface_colour,requireActivity().theme)).build()).build()
        binding.tmdbButton.setOnClickListener {
            tmdbWebsiteIntentBuilder.launchUrl(requireContext(),Uri.parse("https://www.themoviedb.org/"))
        }
        Log.i("Join","IsOn UJser Page")
    }
    fun chooseUserImage( pickPhotoActivityLauncher : ActivityResultLauncher<PickVisualMediaRequest>){
        if(isPhotoPickerAvailable(requireContext())) {

            pickPhotoActivityLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }
    }



}