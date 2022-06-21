package com.justin.pocketmon.plan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.databinding.FragmentPlanBinding
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.home.HomeAdapter
import com.justin.pocketmon.home.HomeViewModel
import com.justin.pocketmon.util.Logger

class PlanFragment : Fragment() {

    /**
     * Lazily initialize our [HomeViewModel].
     */
    private val viewModel by viewModels<PlanViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentPlanBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.isLiveDataDesign = PocketmonApplication.instance.isLiveDataDesign()
        binding.viewModel = viewModel

        binding.recycleviewPlan.layoutManager = LinearLayoutManager(context)
        binding.recycleviewPlan.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        binding.recycleviewPlan.adapter = PlanAdapter(PlanAdapter.OnClickListener {

            Logger.d("click, it=$it")
            viewModel.getArticlesResult()
//            viewModel.delete(it)
        })

// --- submistList here ---
        viewModel.plan.observe(viewLifecycleOwner, Observer {
            (binding.recycleviewPlan.adapter as PlanAdapter).submitList(it)
            binding.swipeRefreshLayout.isRefreshing = false
            Logger.i("Justin Livedata plan = $it")


        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getArticlesResult()
        }
        return binding.root
    }
}

//        binding.recycleviewPlan.adapter = HomeAdapter(PlanAdapter.OnClickListener {
//            Logger.d("click, it=$it")
//            viewModel.delete(it)
//        })
//
//        viewModel.navigationToPublish.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                findNavController().navigate(NavigationDirections.navigationToPublishDialog(it))
//                viewModel.onPublishNavigated()
//            }
//        })
//
//        binding.swipeRefreshLayout.setOnRefreshListener {
//            viewModel.refresh()
//        }
//
//        viewModel.refreshStatus.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                binding.swipeRefreshLayout.isRefreshing = it
//            }
//        })
//
//        ViewModelProvider(requireActivity()).get(MainViewModel::class.java).apply {
//            refresh.observe(viewLifecycleOwner, Observer {
//                it?.let {
//                    viewModel.refresh()
//                    onRefreshed()
//                }
//            })
//        }
//
//        viewModel.liveArticles.observe(viewLifecycleOwner, Observer {
//            Logger.d("viewModel.liveArticles.observe, it=$it")
//            it?.let {
//                binding.viewModel = viewModel
//            }
//        })
//
//        return binding.root
//    }
