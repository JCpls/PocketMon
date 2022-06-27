package com.justin.pocketmon.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.justin.pocketmon.R

enum class HomeTypeFilter(val value: String) {
    WOMEN("women"),
    MEN("men"),
    ACCESSORIES("accessories")
}