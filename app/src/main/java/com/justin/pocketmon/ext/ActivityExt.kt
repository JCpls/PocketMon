package com.justin.pocketmon.ext

import android.app.Activity
import android.view.Gravity
import android.widget.Toast
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.factory.ViewModelFactory

fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as PocketmonApplication).repository
    return ViewModelFactory(repository)
}

fun Activity?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}