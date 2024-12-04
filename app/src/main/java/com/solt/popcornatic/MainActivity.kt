package com.solt.popcornatic

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.imageview.ShapeableImageView
import com.solt.popcornatic.databinding.MainActivityLayoutBinding
import com.solt.popcornatic.databinding.MenuItemCustomViewBinding
import com.solt.popcornatic.user.data.local.files.AccessImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
 lateinit var binding:MainActivityLayoutBinding
 @Inject  lateinit var imageAccess: AccessImage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        lifecycleScope.launch {
            val userImage = imageAccess.readUserImage()
            val userMenuItem = binding.bottomNavBar.menu.findItem(R.id.userPage)
            //Set the drawable to the user picture
            userMenuItem.icon = BitmapDrawable(resources,userImage)


        }

        binding.bottomNavBar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(object :NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                when(destination.id){
                    R.id.tvDetailsPage ,R.id.movieDetailsPage->{
                        binding.bottomNavBar.visibility = View.GONE
                    }
                    else -> binding.bottomNavBar.visibility = View.VISIBLE
                }
            }

        })


    }


}
