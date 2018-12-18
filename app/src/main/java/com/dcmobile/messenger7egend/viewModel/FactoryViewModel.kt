package com.dcmobile.messenger7egend.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dcmobile.messenger7egend.repo.MessageRepository

class FactoryViewModel(private val repository: MessageRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessageDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessageDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}