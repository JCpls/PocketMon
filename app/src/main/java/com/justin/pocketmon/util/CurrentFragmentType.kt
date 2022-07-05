package com.justin.pocketmon.util


import com.justin.pocketmon.R
import com.justin.pocketmon.util.Util.getString


enum class CurrentFragmentType(val value: String) {
    HOME(""),
    CHAT(getString(R.string.chat)),
    PLAN(getString(R.string.plan)),
    PROFILE(getString(R.string.profile)),
    PAYMENT(getString(R.string.payment)),
    DETAIL(""),
    CHECKOUT_SUCCESS(getString(R.string.checkout_success_title))
}
