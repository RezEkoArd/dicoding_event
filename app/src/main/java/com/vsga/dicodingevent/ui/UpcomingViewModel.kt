package com.vsga.dicodingevent.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsga.dicodingevent.data.response.ListEventsItem
import com.vsga.dicodingevent.data.response.ResponseDetailEvent
import com.vsga.dicodingevent.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// Courotine
class UpcomingViewModel : ViewModel(){

    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent: LiveData<List<ListEventsItem>> = _listEvent

    // View Model Detail
    private val _detailEvent = MutableLiveData<ResponseDetailEvent>()
    val detailEvent : LiveData<ResponseDetailEvent> = _detailEvent



    fun getUpcomingEvent(){
        viewModelScope.launch(Dispatchers.IO){
            val client = ApiConfig.getApiService().getEvents("0")
            if (client.isSuccessful){
                _listEvent.postValue(client.body()?.listEvents ?: emptyList())
            }

        }
    }

    fun getDetailEvent(id: String){
        viewModelScope.launch(Dispatchers.IO){
            val client = ApiConfig.getApiService().getDetailEvent(id)
            if(client.isSuccessful){
                _detailEvent.postValue(client.body())
            }
        }
    }
}