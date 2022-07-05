package com.justin.pocketmon.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.databinding.ItemDetailCommentBinding
import com.justin.pocketmon.databinding.ItemPlanEditBinding
import com.justin.pocketmon.plan.edit.PlanEditAdapter

//class DetailCommentAdapter ():
//    ListAdapter<String, RecyclerView.ViewHolder>(DiffCallback) {
//
//        class CommentViewHolder(private var binding: ItemDetailCommentBinding):
//                RecyclerView.ViewHolder(binding.root){
//
//                fun bind(comment: String){
//                    binding.commentData = comment
//                    binding.itemCommentText.text = comment
//
//                    binding.executePendingBindings()
//                }
//            }
//
//        companion object DiffCallback: DiffUtil.ItemCallback<String>(){
//            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
//                return oldItem === newItem
//            }
//            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
//                return oldItem == newItem
//            }
//
//            private const val ITEM_VIEW_TYPE_PLAN = 0x00
//         }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//            return when (viewType) {
//               ITEM_VIEW_TYPE_PLAN -> CommentViewHolder(
//                    ItemDetailCommentBinding.inflate(
//                        LayoutInflater.from(parent.context), parent, false
//                    )
//                )
//                else -> throw ClassCastException("Unknown viewType $viewType")
//            }
//        }
//
//
//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//
//            when (holder) {
//               is CommentViewHolder -> {
//                    holder.bind((getItem(position) as String))
//                }
//            }
//        }
//
//        override fun getItemViewType(position: Int): Int {
//            return ITEM_VIEW_TYPE_PLAN
//        }
//
//
//    }