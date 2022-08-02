package com.justin.pocketmon.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.data.ArticleData
import com.justin.pocketmon.databinding.ItemDreamHomeBinding
import java.text.SimpleDateFormat

class HomeAdapter(private val onClickListener: OnClickListener)  : ListAdapter<ArticleData, RecyclerView.ViewHolder>(DiffCallback) {


    class ArticleViewHolder(private var binding: ItemDreamHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articledata: ArticleData, onClickListener: OnClickListener) {

            binding.articledData = articledata
            binding.textAuthorName.text = articledata.name
            binding.textTitle.text = articledata.title
            binding.textCategory.text = articledata.category

            val sdf = SimpleDateFormat("MM.dd.yyyy")
            val time = articledata.createdTime?.seconds?.times(1000L)
            val dataTime = sdf.format(time)
            binding.textTime.text = dataTime

            binding.root.setOnClickListener{onClickListener.onClick(articledata)}

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ArticleViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDreamHomeBinding.inflate(layoutInflater, parent, false)

                return ArticleViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArticleViewHolder -> {
                val articleItem = getItem(position) as ArticleData

                holder.bind(articleItem, onClickListener)
            }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<ArticleData>() {
        override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
            return oldItem == newItem
        }

    }
    class OnClickListener(val clickListener: (articledata: ArticleData) -> Unit) {
        fun onClick(articledata: ArticleData) = clickListener(articledata)
    }

}