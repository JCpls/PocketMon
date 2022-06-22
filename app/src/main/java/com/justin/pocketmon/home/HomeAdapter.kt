package com.justin.pocketmon.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.databinding.ItemDreamHomeBinding
import java.text.SimpleDateFormat

class HomeAdapter(private val onClickListener: OnClickListener)  : ListAdapter<Articledata, RecyclerView.ViewHolder>(DiffCallback) {


    class ArticleViewHolder(private var binding: ItemDreamHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articledata: Articledata, onClickListener: OnClickListener) {

            binding.articledData = articledata
            binding.textAuthorName.text = articledata.name
            binding.textTitle.text = articledata.title
            binding.textContent.text = articledata.content
            binding.textCategory.text = articledata.category


            if (articledata.category == "0") {
                binding.textCategory.setTextColor(Color.rgb(0, 0, 200))
            } else if (articledata.category == "1") {
                binding.textCategory.setTextColor(Color.rgb(0, 0, 200))
            } else if (articledata.category == "2") {
                binding.textCategory.setTextColor(Color.rgb(0, 160, 0))
            } else if (articledata.category == "3") {
                binding.textCategory.setTextColor(Color.rgb(0, 160, 0))
            } else if (articledata.category == "4") {
                binding.textCategory.setTextColor(Color.rgb(0, 160, 0))
            } else {
                binding.textCategory.setTextColor(Color.rgb(160, 0, 160))
            }


            val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm")
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
                val articleItem = getItem(position) as Articledata

                holder.bind(articleItem, onClickListener)
            }
        }

    }


    companion object DiffCallback : DiffUtil.ItemCallback<Articledata>() {
        override fun areItemsTheSame(oldItem: Articledata, newItem: Articledata): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Articledata, newItem: Articledata): Boolean {
            return oldItem == newItem
        }

    }
    class OnClickListener(val clickListener: (articledata: Articledata) -> Unit) {
        fun onClick(articledata: Articledata) = clickListener(articledata)
    }

}