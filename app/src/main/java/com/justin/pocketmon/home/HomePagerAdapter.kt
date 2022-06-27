package com.justin.pocketmon.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.justin.pocketmon.R

class HomePagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    var fragments : ArrayList<Fragment> = arrayListOf(
        HomeChildFragment.newInstance("mydream"),
        HomeChildFragment.newInstance("friendsdream"),
        HomeChildFragment.newInstance("populardream")

    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}