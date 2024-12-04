package com.solt.popcornatic.user.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.AddFavouriteLayoutBinding
import com.solt.popcornatic.user.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFavouritePage : DialogFragment() {
    lateinit var binding:AddFavouriteLayoutBinding
    val viewModel:UserViewModel by viewModels<UserViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddFavouriteLayoutBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener {
            val text = binding.nameEt.text.toString()
            if(text.isBlank()){
                binding.nameEt.error ="Text empty"
                return@setOnClickListener
            }
            viewModel.addANewFavourite(text)
            dismiss()
        }
    }
}