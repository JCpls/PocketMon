package com.justin.pocketmon


import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        GlideApp.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.image_bg_pocketmon)
                    .error(R.drawable.image_bg_pocketmon)
            )
            .into(imgView)
    }
}

//@BindingAdapter("images")
//fun bindRecyclerViewWithImages(recyclerView: RecyclerView, images: List<String>?) {
//    images?.let {
//        recyclerView.adapter?.apply {
//            when (this) {
//                is DetailGalleryAdapter -> {
//                    submitImages(it)
//                }
//            }
//        }
//    }
//}