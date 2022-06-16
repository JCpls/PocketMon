package com.justin.pocketmon

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Log.i
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.pocketmon.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.util.logging.Logger


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()

        // Create a new user with a first and last name
        // Create a new user with a first and last name
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["last"] = "Lovelace"
        user["born"] = 1815

// Add a new document with a generated ID

// Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        setupToolbar()
        setupBottomNav()
//        setupDrawer()
//        setupNavController()

    }


    /**
     * Set up [BottomNavigationView], add badge view through [BottomNavigationMenuView] and [BottomNavigationItemView]
     * to display the count of Cart
     */
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

//                    when (viewModel.isLoggedIn) {
//                        true -> {
//                            findNavController(R.id.myNavHostFragment).navigate(
//                                NavigationDirections.navigateToProfileFragment(
//                                    viewModel.user.value
//                                )
//                            )
//                        }
//                        false -> {
//                            findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToLoginDialog())
//                            return@setOnItemSelectedListener false
//                        }
//                    }
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToProfileFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
// badge
//        val menuView = binding.bottomNavView.getChildAt(0) as BottomNavigationMenuView
//        val itemView = menuView.getChildAt(2) as BottomNavigationItemView
//        val bindingBadge = BadgeBottomBinding.inflate(LayoutInflater.from(this), itemView, true)
//        bindingBadge.lifecycleOwner = this
//        bindingBadge.viewModel = viewModel
    }

    /**
     * Set up [NavController.addOnDestinationChangedListener] to record the current fragment, it better than another design
     * which is change the [CurrentFragmentType] enum value by [MainViewModel] at [onCreateView]
     */
//    private fun setupNavController() {
//        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
//            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
//                R.id.homeFragment -> CurrentFragmentType.HOME
//                R.id.chatFragment -> CurrentFragmentType.CHAT
//                R.id.planFragment -> CurrentFragmentType.PLAN
//                R.id.profileFragment -> CurrentFragmentType.PROFILE
//                else -> viewModel.currentFragmentType.value
//            }
//        }
//    }

//    private fun setupToolbar() {
//
//        binding.toolbar.setPadding(0, statusBarHeight, 0, 0)
//
//        launch {
//
//            val dpi = resources.displayMetrics.densityDpi.toFloat()
//            val dpiMultiple = dpi / DisplayMetrics.DENSITY_DEFAULT
//
//            val cutoutHeight = getCutoutHeight()
//
//            Logger.i("====== ${Build.MODEL} ======")
//            Logger.i("$dpi dpi (${dpiMultiple}x)")
//            Logger.i("statusBarHeight: ${statusBarHeight}px/${statusBarHeight / dpiMultiple}dp")
//
//            when {
//                cutoutHeight > 0 -> {
//                    Logger.i("cutoutHeight: ${cutoutHeight}px/${cutoutHeight / dpiMultiple}dp")
//
//                    val oriStatusBarHeight = resources.getDimensionPixelSize(R.dimen.height_status_bar_origin)
//
//                    binding.toolbar.setPadding(0, oriStatusBarHeight, 0, 0)
//                    val layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
//                    layoutParams.gravity = Gravity.CENTER
//
//                    when (Build.MODEL) {
//                        "Pixel 5" -> { Logger.i("Build.MODEL is ${Build.MODEL}") }
//                        else -> { layoutParams.topMargin = statusBarHeight - oriStatusBarHeight }
//                    }
//                    binding.imageToolbarLogo.layoutParams = layoutParams
//                    binding.textToolbarTitle.layoutParams = layoutParams
//                }
//            }
//            Logger.i("====== ${Build.MODEL} ======")
//        }
//    }

}