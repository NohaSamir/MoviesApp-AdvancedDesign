package com.noha.moviesadvanced.presentaion.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noha.moviesadvanced.data.source.network.model.ResponseWrapper

open class BaseViewModel : ViewModel() {

    val showMessage: LiveData<String>
        get() = _showMessage
    protected val _showMessage: MutableLiveData<String> = MutableLiveData()

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    protected val _isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun onResponseError(error: ResponseWrapper<Any>) {
        when (error) {
            is ResponseWrapper.GenericError -> {
                _showMessage.value = error.error?.message
            }

            is ResponseWrapper.NetworkError -> {
                _showMessage.value = error.message
            }

            is ResponseWrapper.Success -> {
                // Do Nothing
            }
        }
    }
}