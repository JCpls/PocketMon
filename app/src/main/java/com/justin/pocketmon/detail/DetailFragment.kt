package com.justin.pocketmon.detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.pocketmon.MainActivity
import com.justin.pocketmon.databinding.FragmentDetailBinding
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.login.UserManager
import com.justin.pocketmon.util.Logger

class DetailFragment : Fragment() {

    private val viewModel by viewModels<DetailViewModel> { getVmFactory(DetailFragmentArgs.fromBundle(requireArguments()).articleKey) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // add data from DetailFragment to Plan collection
        val db = FirebaseFirestore.getInstance()
        val document = db.collection("Plans").document()

        binding.buttonDetailAdd.setOnClickListener{

            val plan = Plan()
            plan.id = document.id

            viewModel.selectedDream.value?.let {
            Logger.i("1st check argument from detailPage => $plan ")
                plan.title = it.title
                plan.image = it.image
                plan.ownerId = UserManager.user.id
                plan.name = UserManager.user.name
                plan.description = listOf(it.content)
                plan.degree = it.category.toLong()
                plan.createdTime = com.google.firebase.Timestamp.now()

            }
            viewModel.publishPlan(plan)
            Logger.i("2nd check argument from detailPage => $plan ")

        // call out to execute fun: navigateToPlan in MainActivity
            (activity as MainActivity).navigateToPlan()

        }

        val detailCommentAdapter = DetailCommentAdapter(viewModel)
        binding.detailCommentRecyclerview.adapter = detailCommentAdapter

        // for recyclerView
        viewModel.isLiveCommentListReady.observe(viewLifecycleOwner) {
            Logger.i("isLiveCommentListReady = $it")

            viewModel.liveComment.observe(viewLifecycleOwner) {
                Logger.i("liveCommentList = $it")
                detailCommentAdapter.submitList(it)
            }
        }

        binding.buttonDetailComment.setOnClickListener{
            viewModel.navigateToCommentDialog()
        }

        // --- submistList here ---
        viewModel.navigateToPlanPage.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToPlanFragment(  ))
                    viewModel.onDetailtoPlanPageNavigated()
                }
            }
        )

        viewModel.navigateToChat.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToChatFragment())
                    viewModel.onDetailtoChatRoomNavigated()
                }
            }
        )

        viewModel.navigateToComment.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToCommentDialog(it))
                    viewModel.onDetailtoCommentDialogNavigated()
                }
            }
        )

        viewModel.leaveDetail.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    if (it) findNavController().popBackStack()
                }
            }
        )

        return binding.root
    }
}