package com.solt.popcornatic.movies.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.databinding.ProductionCompanyDetailsLayoutBinding
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.ProductionCompanies.ProductionCompanyDetail
import com.solt.popcornatic.movies.data.repository.MovieRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

const val COMPANY_ID = "companyId"
@AndroidEntryPoint
class ProductionCompanyDetailsBottomDialog:BottomSheetDialogFragment() {
    //We are not using viewmodel
    @Inject lateinit var movieRepository: MovieRepositoryImpl
    lateinit var binding: ProductionCompanyDetailsLayoutBinding
    //Null values if set can revert back to null if the view lifecycle is destroyed
    //Watch out and check the logs


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductionCompanyDetailsLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val companyId = arguments?.getInt(COMPANY_ID)
        if (companyId == null){
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {

                val result = movieRepository.getProductionCompanyDetailsById(companyId!!)
                Log.i("CompanyId",result.toString())

                when(result){
                    is ApiResult.Failure.ApiFailure -> {
                        findNavController().popBackStack()
                    }
                    is ApiResult.Failure.NetworkFailure -> {
                        findNavController().popBackStack()
                    }
                    is ApiResult.Success<*> -> {
                        val data = result.data as ProductionCompanyDetail

                        binding.apply {
                            Log.i("CompanyId",data.logoPath.toString())
                            Glide.with(logo).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${data.logoPath}").into(logo)
                            name.text = data.name
                            location.text = "${data.headquarters?:""}, ${data.originCountry?:""}"

                        }
                    }
                }



        }

    }


}