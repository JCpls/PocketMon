package com.justin.pocketmon.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.databinding.FragmentHomeBinding
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.util.ServiceLocator.repository

class HomeFragment: Fragment() {

    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

// viewPager

        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val viewPagerAdapter = HomePagerAdapter(this)
        val viewPager = binding.homeViewpager2
        viewPager.adapter = viewPagerAdapter


        val catalogPageArray = arrayOf(
            "My",
            "好友",
            "流行"
        )

        val tabLayout = binding.homeTablayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = catalogPageArray[position]

        } .attach()

// recyclerview
        val viewModel = HomeViewModel()

// stagger style recyclerview
        binding.recycleviewHome.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

//      val adapter = HomeAdapter()
//      binding.recycleviewHome.adapter = adapter

        binding.recycleviewHome.adapter = HomeAdapter(
            HomeAdapter.OnClickListener {
                viewModel.navigateToDetail(it)
            }
        )

//        binding.swipeRefreshLayout.setOnRefreshListener {
//            viewModel.refresh()
//        }

        viewModel.articleData.observe(viewLifecycleOwner, Observer {
            (binding.recycleviewHome.adapter as HomeAdapter).submitList(it)
            (binding.recycleviewHome.adapter as HomeAdapter).notifyDataSetChanged()
//            adapter.submitList(it)
//            adapter.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false

        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getData()
        }


        binding.buttonAdd.setOnClickListener {
            this.findNavController().navigate(NavigationDirections.navigateToHomeEditFragment())
        }


// handle navigation to detail
        viewModel.navigateToDetail.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
                    viewModel.onDetailNavigated()
                }
            }
        )


        return binding.root

    }
}