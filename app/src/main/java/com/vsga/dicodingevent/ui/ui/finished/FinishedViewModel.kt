package com.vsga.dicodingevent.ui.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsga.dicodingevent.data.response.ListEventsItem
import com.vsga.dicodingevent.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FinishedViewModel : ViewModel() {

    //list Event
    private val _finishedEvent = MutableLiveData<List<ListEventsItem>>()
    val finishedEvent : LiveData<List<ListEventsItem>> = _finishedEvent

    fun getFinishedEvent(){
        viewModelScope.launch(Dispatchers.IO){
            val client = ApiConfig.getApiService().getEvents("0", "")
            if(client.isSuccessful){
                _finishedEvent.postValue(client.body()?.listEvents ?: emptyList())
            }
        }
    }

}