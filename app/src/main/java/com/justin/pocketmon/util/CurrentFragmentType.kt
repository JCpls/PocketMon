package com.justin.pocketmon.util


import com.justin.pocketmon.R
import com.justin.pocketmon.util.Util.getString


enum class CurrentFragmentType(val value: String) {
    INTRO(""),
    HOME(""),
    CHAT(getString(R.string.chat)),
    PLAN(getString(R.string.plan)),
    PROFILE(getString(R.string.profile)),
    DETAIL(""),
    PLANEDIT("")
}
