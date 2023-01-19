package com.android.practicalexamround.api

import com.android.practicalexamround.model.ListDataModel
import com.android.practicalexamround.model.ListDataModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIs {

    @GET("list")
    suspend fun getDataList(
        @Query("page") page: String,
        @Query("limit") limit: Int
    ): Response<List<ListDataModelItem>>

}