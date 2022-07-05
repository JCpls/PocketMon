package com.justin.pocketmon.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.databinding.ItemDetailCommentBinding
import com.justin.pocketmon.databinding.ItemPlanBinding
import com.justin.pocketmon.plan.PlanAdapter
import com.justin.pocketmon.util.Logger

class CommentAdapter () :
    ListAdapter<Articledata, RecyclerView.ViewHolder>(DiffCallback) {

    class CommentViewHolder(private var binding: ItemDetailCommentBinding):

        RecyclerView.ViewHolder(binding.root) {

        fun bind(articledata: Articledata) {

            Logger.i("CommentAdapter comment list articledata = ${articledata}")

            binding.commentData = articledata
            binding.itemCommentText.text = articledata.comment.last()
            binding.itemCommentUser.text = articledata.name

            binding.executePendingBindings()

        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Articledata>() {
        override fun areItemsTheSame(oldItem: Articledata, newItem: Articledata): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Articledata, newItem: Articledata): Boolean {
            return oldItem.uid == newItem.uid
        }

        private const val ITEM_VIEW_TYPE_PLAN = 0x00
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_PLAN -> CommentViewHolder(
                ItemDetailCommentBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is CommentViewHolder -> {
                holder.bind((getItem(position) as Articledata))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_PLAN
    }
}


