package com.justin.pocketmon.ext


import androidx.fragment.app.Fragment
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.data.*
import com.justin.pocketmon.factory.*

/* Extension functions for Fragment.
*/
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as PocketmonApplication).repository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(articledata: Articledata): ArticleViewModelFactory {
    val repository = (requireContext().applicationContext as PocketmonApplication).repository
    return ArticleViewModelFactory(articledata, repository)
}

fun Fragment.getVmFactory(plan: Plan): PlanViewModelFactory {
    val repository = (requireContext().applicationContext as PocketmonApplication).repository
    return PlanViewModelFactory(plan, repository)
}

fun Fragment.getVmFactory(broadcast: Broadcast): BroadcastViewModelFactory {
    val repository = (requireContext().applicationContext as PocketmonApplication).repository
    return BroadcastViewModelFactory(broadcast, repository)
}