package com.justin.pocketmon.detail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.justin.pocketmon.databinding.FragmentDetailBinding
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.ext.getVmFactory

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

        viewModel.navigateToPlanPage.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToPlanFragment())
                    viewModel.onDetailtoPlanPageNavigated()
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