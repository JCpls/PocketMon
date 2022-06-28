package com.justin.pocketmon.ext

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.auth.User
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.factory.ArticleViewModelFactory
import com.justin.pocketmon.factory.PlanViewModelFactory
import com.justin.pocketmon.factory.TypeViewModelFactory
import com.justin.pocketmon.factory.ViewModelFactory

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


//fun Fragment.getVmFactory(user: User?): ProfileViewModelFactory {
//    val repository = (requireContext().applicationContext as PocketmonApplication).repository
//    return ProfileViewModelFactory(repository, user)
//}
//
//fun Fragment.getVmFactory(product: Product): ProductViewModelFactory {
//    val repository = (requireContext().applicationContext as PocketmonApplication).repository
//    return ProductViewModelFactory(repository, product)
//}
//
//fun Fragment.getVmFactory(catalogType: CatalogTypeFilter): CatalogItemViewModelFactory {
//    val repository = (requireContext().applicationContext as PocketmonApplication).repository
//    return CatalogItemViewModelFactory(repository, catalogType)
//}