package com.justin.pocketmon.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

// viewPager
        val binding = FragmentHomeBinding.inflate(inflater)
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
        val adapter = HomeAdapter()
        binding.recycleviewHome.adapter = adapter

        viewModel.articleData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false

        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getData()
        }


        binding.buttonAdd.setOnClickListener {
            this.findNavController().navigate(NavigationDirections.navigateToHomeEditFragment())
        }




        return binding.root

    }
}