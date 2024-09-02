package com.vsga.dicodingevent.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsga.dicodingevent.data.response.ResponseDetailEvent
import com.vsga.dicodingevent.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// Courotine
class DetailViewModel : ViewModel(){

    // View Model Detail
    private val _detailEvent = MutableLiveData<ResponseDetailEvent>()
    val detailEvent : LiveData<ResponseDetailEvent> = _detailEvent


    fun getDetailEvent(id: String){
        viewModelScope.launch(Dispatchers.IO){
            val client = ApiConfig.getApiService().getDetailEvent(id)
            if(client.isSuccessful){
                _detailEvent.postValue(client.body())
            }
        }
    }
}