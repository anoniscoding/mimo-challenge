package com.anoniscoding.mimo.ui.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anoniscoding.mimo.ui.presentation.state.DataState
import com.anoniscoding.mimo.ui.presentation.state.Event
import com.anoniscoding.mimo.ui.presentation.state.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel<T, U> : ViewModel() {
    protected val _dataStates: MutableLiveData<DataState<T>> = MutableLiveData()
    val dataStates: LiveData<DataState<T>> = _dataStates

    protected val _viewEffects: SingleLiveEvent<U> = SingleLiveEvent()
    val viewEffects: SingleLiveEvent<U> = _viewEffects

    protected fun launchRequest(block: suspend () -> Unit): Job {
        val currentViewState = getViewState()
        return viewModelScope.launch {
            try {
                _dataStates.postValue(DataState.Loading(currentViewState))
                block()
            } catch (exception: Exception) {
                _dataStates.postValue(DataState.Error(currentViewState, Event(exception)))
            }
        }
    }

    protected fun getViewState(): T? = _dataStates.value?.toData()
}