package com.vsga.dicodingevent.ui.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsga.dicodingevent.data.response.ListEventsItem
import com.vsga.dicodingevent.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // variable sementara
    private val _allEvent = MutableLiveData<List<ListEventsItem>>()
    val allEvent : LiveData<List<ListEventsItem>> = _allEvent

    fun getAllEvent(){
        viewModelScope.launch(Dispatchers.IO){
            val client = ApiConfig.getApiService().getEvents("-1")
            if(client.isSuccessful){
                _allEvent.postValue(client.body()?.listEvents ?: emptyList())
            }
        }
    }
}