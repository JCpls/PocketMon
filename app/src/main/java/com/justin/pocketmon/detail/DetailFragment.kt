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
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.ext.getVmFactory
import com.squareup.okhttp.internal.Internal.logger
import java.sql.Timestamp

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
            // livedata 必須要 .value 才能夠賦值
            viewModel.selectedDream.value?.let {
                Log.d("justin","初檢查從detail帶過來的資料 => $plan ")
                plan.title = it.title
                plan.image = it.image
                plan.ownerId = it.name
                plan.description = listOf(it.content)
                plan.degree = it.category.toLong()
                plan.createdTime = com.google.firebase.Timestamp.now()
            }
            viewModel.publishPlan(plan)
            Log.d("justin","再檢查從detail帶過來的資料 => $plan ")

// call out to execute fun: navigateToPlan in MainActivity
            (activity as MainActivity).navigateToPlan()
//          viewModel.navigateToStartPlan()

        }

        binding.buttonDetailMessage.setOnClickListener{
            viewModel.navigateToChatRoom()
        }

        binding.buttonDetailMessage.setOnClickListener{
            viewModel.navigateToCommentDialog()
        }

        // --- submistList here ---

        viewModel.navigateToPlanPage.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToPlanFragment())
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
                    findNavController().navigate(NavigationDirections.navigateToCommentDialog())
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






//   binding.recyclerDetailGallery.adapter = DetailGalleryAdapter()
//        binding.recyclerDetailCircles.adapter = DetailCircleAdapter()
//
//        viewModel.selectedDream.observe(viewLifecycleOwner, Observer {
//            Logger.i("selectedDream = $it")
//        })
//
//        val linearSnapHelper = LinearSnapHelper().apply {
//            attachToRecyclerView(binding.recyclerDetailGallery)
//        }
//
//        binding.recyclerDetailGallery.setOnScrollChangeListener { _, _, _, _, _ ->
//            viewModel.onGalleryScrollChange(
//                binding.recyclerDetailGallery.layoutManager,
//                linearSnapHelper
//            )
//        }
//  以上 ----binding.recyclerDetailGallery.adapter = DetailGalleryAdapter()

// ---- set the initial position to the center of infinite gallery
//        viewModel.selectedDream.value?.let { articleData ->
//            binding.recyclerDetailGallery
//                .scrollToPosition(articleData.image.size * 100)
//
//            viewModel.snapPosition.observe(
//                viewLifecycleOwner,
//                Observer {
//                    (binding.recyclerDetailCircles.adapter as DetailCircleAdapter).selectedPosition.value = (it % articleData.image.size)
//                }
//            )
//        }


//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        val application = requireNotNull(activity).application
//        val binding = DetailFragmentBinding.inflate(inflater)
//        binding.lifecycleOwner = this
//
//        val product:Product = DetailFragmentArgs.fromBundle(requireArguments()).selectedProduct
//
//
//        val viewModelFactory = DetailPageViewModelFactory(product, application)
//        binding.detailViewModel = ViewModelProvider(
//            this, viewModelFactory).get(DetailPageViewModel::class.java)
//
//
//        binding.detailItemNumber.text = product.id.toString()
//        binding.detailItemPrice.text = "NT$" + product.price.toString()
//
//
//        if(product.sizes.size == 1){
//            binding.detailItemSizeContent.text = product.sizes.first()
//        }else{
//            binding.detailItemSizeContent.text = "${product.sizes.first()} - ${product.sizes.last()}"
//        }
////庫存的迴圈
//        var productStock = 0
//        for(i in product.variants){
//            productStock += i.stock
//        }
//        binding.detailItemStockContent.text = productStock.toString()
//
//
////4thMay android navcontroller in fragment 設定間單的點擊事件給detail頁的左上小圓圈 直接帶回上一頁
//        binding.imageView2.setOnClickListener {
//            findNavController().navigateUp()
//        }
//
//
//        binding.detailItemTextureContent.text = product.texture
//        binding.detailItemOriginContent.text = product.place
//