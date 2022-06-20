package com.justin.pocketmon.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.justin.pocketmon.R

class DetailFragment : Fragment() {

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
//        return binding.root
//    }
}