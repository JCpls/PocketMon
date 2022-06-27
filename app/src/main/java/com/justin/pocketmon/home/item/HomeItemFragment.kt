package com.justin.pocketmon.home.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.justin.pocketmon.home.HomeTypeFilter

//class HomeItemFragment(private val homeType: HomeTypeFilter) : Fragment() {
//
//    private val viewModel by viewModels<HomeItemViewModel> { getVmFactory(homeType) }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val binding = FragmentCatalogItemBinding.inflate(inflater, container, false)
//
//        binding.lifecycleOwner = viewLifecycleOwner
//
//        binding.viewModel = viewModel
//
//        binding.recyclerCatalogItem.adapter = PagingAdapter(
//            PagingAdapter.OnClickListener {
//                viewModel.navigateToDetail(it)
//            }
//        )
//
//        viewModel.navigateToDetail.observe(
//            viewLifecycleOwner,
//            Observer {
//                it?.let {
//                    findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
//                    viewModel.onDetailNavigated()
//                }
//            }
//        )
//
//        viewModel.pagingDataProducts.observe(
//            viewLifecycleOwner,
//            Observer {
//                (binding.recyclerCatalogItem.adapter as PagingAdapter).submitList(it)
//            }
//        )
//
//        binding.layoutSwipeRefreshCatalogItem.setOnRefreshListener {
//            viewModel.refresh()
//        }
//
//        viewModel.status.observe(
//            viewLifecycleOwner,
//            Observer {
//                it?.let {
//                    if (it != LoadApiStatus.LOADING)
//                        binding.layoutSwipeRefreshCatalogItem.isRefreshing = false
//                }
//            }
//        )
//
//        return binding.root
//    }
//}
//}