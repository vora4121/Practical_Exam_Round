package com.android.practicalexamround.utils

import android.app.Activity
import android.content.Intent

fun <T> Activity.goTo(it: Class<T>) {
    startActivity(Intent(this, it))
}
