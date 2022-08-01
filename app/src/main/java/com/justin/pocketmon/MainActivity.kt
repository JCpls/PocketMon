package com.justin.pocketmon

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.pocketmon.data.Broadcast
import com.justin.pocketmon.data.Chatroom
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.databinding.ActivityMainBinding
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.login.UserManager
import com.justin.pocketmon.login.UserManager.clear
import com.justin.pocketmon.util.CurrentFragmentType
import com.justin.pocketmon.util.Logger


class MainActivity : AppCompatActivity() {

// --------------------------
    val viewModel by viewModels<MainViewModel> { getVmFactory() }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    // setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


    // setupToolbar()
        setupBottomNav()

    // setupDrawer()
        setupNavController()

    // will make it automatically log out
        UserManager.clear()
    }

    private fun setupBottomNav() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.navigation_home -> {

                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToHomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_chat -> {

                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToChatFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_plan -> {

                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToPlanFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_profile -> {

                    when (viewModel.isLoggedIn) {
                        true -> {
                            findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToProfileFragment()
                            )
                            return@setOnItemSelectedListener true
                        }
                        false -> {
                            findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToLoginDialog())
                            return@setOnItemSelectedListener false
                        }
                    }
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    // to change the bottomNav icon when Detail page navigate to Plan page
    fun navigateToPlan() {
        binding.bottomNavView.selectedItemId = R.id.navigation_plan
    }

    /**
     * Set up [NavController.addOnDestinationChangedListener] to record the current fragment, it better than another design
     * which is change the [CurrentFragmentType] enum value by [MainViewModel] at [onCreateView]
     */
    private fun setupNavController() {
        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.homeFragment -> CurrentFragmentType.HOME
                R.id.chatFragment -> CurrentFragmentType.CHAT
                R.id.chatroomFragment -> CurrentFragmentType.CHATROOM
                R.id.planFragment -> CurrentFragmentType.PLAN
                R.id.profileFragment -> CurrentFragmentType.PROFILE
                R.id.detailFragment -> CurrentFragmentType.DETAIL
                R.id.planEditFragment -> CurrentFragmentType.PLANEDIT

                else -> viewModel.currentFragmentType.value
            }
        }
    }
}