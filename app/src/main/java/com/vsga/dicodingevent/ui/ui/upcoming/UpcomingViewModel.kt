package com.vsga.dicodingevent.ui.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsga.dicodingevent.data.response.ListEventsItem
import com.vsga.dicodingevent.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class UpcomingViewModel : ViewModel() {

    // UpcomingEvent
    private val _upcomingEvent = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvent : LiveData<List<ListEventsItem>> = _upcomingEvent

    fun getUpcomingEvent() {
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getEvents("1", "")
            if(client.isSuccessful){
                _upcomingEvent.postValue(client.body()?.listEvents ?: emptyList())
            }
        }
    }
}