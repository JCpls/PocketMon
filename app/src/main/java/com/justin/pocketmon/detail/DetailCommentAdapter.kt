package com.justin.pocketmon.detail

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.justin.pocketmon.GlideApp
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Comment
import com.justin.pocketmon.databinding.ItemDetailCommentBinding
import com.justin.pocketmon.databinding.ItemPlanEditBinding
import com.justin.pocketmon.plan.edit.PlanEditAdapter
import com.justin.pocketmon.util.Logger

class DetailCommentAdapter (var viewModel: DetailViewModel):
    ListAdapter<Comment, RecyclerView.ViewHolder>(DiffCallback) {

        class CommentViewHolder(private var binding: ItemDetailCommentBinding,var viewModel: DetailViewModel ):
                RecyclerView.ViewHolder(binding.root){

                fun bind(comment: Comment){
                    binding.commentData = Comment()
                    binding.itemCommentText.text = comment.content
                    binding.itemCommentUser.text = comment.authorName

                    Logger.i("authorImage = ${comment.authorImage}")

                // viewBinding for comment's autherImage
                    comment.authorImage.let {
                        val imgUri = it.toUri().buildUpon().build()
                        GlideApp.with(binding.itemCommentCircle.context)
                            .load(imgUri)
                            .circleCrop()
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.image_bg_pocketmon)
                                    .error(R.drawable.image_bg_pocketmon)
                            )
                            .into(binding.itemCommentCircle)
                    }


                    binding.executePendingBindings()
                }
        }

        companion object DiffCallback: DiffUtil.ItemCallback<Comment>(){
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem === newItem
            }
            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }

            private const val ITEM_VIEW_TYPE_PLAN = 0x00
         }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
               ITEM_VIEW_TYPE_PLAN -> CommentViewHolder(
                    ItemDetailCommentBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ),viewModel
                )
                else -> throw ClassCastException("Unknown viewType $viewType")
            }
        }


        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            when (holder) {
               is CommentViewHolder -> {
                    holder.bind((getItem(position) as Comment))
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return ITEM_VIEW_TYPE_PLAN
        }


    }