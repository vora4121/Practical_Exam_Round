package com.android.practicalexamround.model

import java.io.Serializable

data class ListDataModel<T>(val listDataModel: ArrayList<T>) : Serializable

data class ListDataModelItem(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
): Serializable