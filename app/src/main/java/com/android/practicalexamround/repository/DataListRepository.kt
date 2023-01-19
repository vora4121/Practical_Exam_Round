package com.android.practicalexamround.repository

import androidx.lifecycle.MutableLiveData
import com.android.practicalexamround.api.APIs
import com.android.practicalexamround.model.ListDataModel
import com.android.practicalexamround.model.ListDataModelItem
import com.android.practicalexamround.utils.NetworkResult
import javax.inject.Inject

class DataListRepository @Inject constructor(private val apIs: APIs) {
    private val _LiveData = MutableLiveData<NetworkResult<List<ListDataModelItem>>>()
    val getLiveData get() = _LiveData

    // TODO Get data from server and send to model class
    suspend fun getDataList(page: String, limit: Int){
        _LiveData.postValue(NetworkResult.Loading())
        val response = apIs.getDataList(page, limit)
        if (response.isSuccessful && response.body() != null){
            with(response.body()!!) {
                _LiveData.postValue(NetworkResult.Success(this))
            }
        }else{
            _LiveData.postValue(NetworkResult.Error("Something went wrong, Please try again"))
        }
    }

}