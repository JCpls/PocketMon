package com.justin.pocketmon.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.justin.pocketmon.R

//使用小安老師的newInstance作法(HomePagerAdapter裡面）搭配這邊

class HomeChildFragment() : Fragment() {

    lateinit var viewModel: HomeViewModel

    companion object {
        fun newInstance(type: String): HomeChildFragment {
            val f = HomeChildFragment()
            // Supply index input as an argument.
            val args = Bundle()
            args.putString("type", type)
            f.setArguments(args)
            return f
        }
    }
}