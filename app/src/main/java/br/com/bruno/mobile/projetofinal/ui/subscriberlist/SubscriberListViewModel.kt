package br.com.bruno.mobile.projetofinal.ui.subscriberlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bruno.mobile.projetofinal.data.db.entity.SubscriberEntity
import br.com.bruno.mobile.projetofinal.repository.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberListViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {

    private val _allSubscribersEvent = MutableLiveData<List<SubscriberEntity>>()
    val allSubscribersEvent: LiveData<List<SubscriberEntity>>
        get() = _allSubscribersEvent

    fun getSubscibers() = viewModelScope.launch {
        _allSubscribersEvent.postValue(repository.getAllSubscribers())
    }

}