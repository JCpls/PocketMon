package com.justin.pocketmon.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.pocketmon.comment.CommentViewModel
import com.justin.pocketmon.data.ArticleData
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.detail.DetailViewModel

@Suppress("UNCHECKED_CAST")
class ArticleViewModelFactory constructor(
    private val articledata: ArticleData,
    private val repository: PocketmonRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(DetailViewModel::class.java) ->
                    DetailViewModel(articledata, repository)

                isAssignableFrom(CommentViewModel::class.java) ->
                    CommentViewModel(articledata, repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}