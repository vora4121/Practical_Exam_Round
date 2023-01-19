package com.android.practicalexamround.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.practicalexamround.repository.DataListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DataListRepository) : ViewModel() {

    val LiveData get() = repository.getLiveData

    fun getData(page: String, limit: Int){
        viewModelScope.launch {
            repository.getDataList(page, limit)
        }
    }

}