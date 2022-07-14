package com.justin.pocketmon.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.databinding.FragmentChatBinding
import com.justin.pocketmon.databinding.FragmentPlanBinding
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.home.HomeViewModel
import com.justin.pocketmon.plan.PlanAdapter
import com.justin.pocketmon.plan.PlanViewModel
import com.justin.pocketmon.plan.edit.PlanEditAdapter
import com.justin.pocketmon.util.Logger

class ChatFragment : Fragment() {

    /**
     * Lazily initialize our [HomeViewModel].
     */
    private val viewModel by viewModels<ChatViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentChatBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.isLiveDataDesign = PocketmonApplication.instance.isLiveDataDesign()
        binding.viewModel = viewModel

//        binding.recycleviewBroadcast.layoutManager = LinearLayoutManager(context)
//        binding.recycleviewBroadcast.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.HORIZONTAL))
        binding.recycleviewBroadcast.adapter = ChatAdapter(ChatAdapter.OnClickListener {

//            Logger.d("click, it=$it")
            viewModel.getBroadcastsResult()
//        handle navigation to detail
//            viewModel.navigateToPlanEdit(it)
//            Logger.d("click, it=$it")
//            viewModel.delete(it)
        })

// --- submistList here ---

        viewModel.broadcast.observe(viewLifecycleOwner, Observer {
            (binding.recycleviewBroadcast.adapter as ChatAdapter).submitList(it)
            (binding.recycleviewBroadcast.adapter as ChatAdapter).notifyDataSetChanged()

//            adapter.submitList(it)

            binding.swipeRefreshLayout.isRefreshing = false
            Logger.i("Justin ChatFragment Livedata broadcast = $it")

        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getBroadcastsResult()
        }

//        handle navigation to detail
//        viewModel.navigateToPlanEdit.observe(
//            viewLifecycleOwner,
//            Observer {
//                it?.let {
//                    findNavController().navigate(NavigationDirections.navigateToPlanEditFragment(it))
//                    viewModel.onPlanNavigated()
//                }
//            }
//        )

        return binding.root
    }
}