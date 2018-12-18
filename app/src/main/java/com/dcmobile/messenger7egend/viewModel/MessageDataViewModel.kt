package com.dcmobile.messenger7egend.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.dcmobile.messenger7egend.MessageWithAttachment
import com.dcmobile.messenger7egend.repo.MessageRepository


class MessageDataViewModel(repository: MessageRepository) : ViewModel() {

    private var messages: LiveData<List<MessageWithAttachment>> = repository.getMessages()

    fun getMessages(): LiveData<List<MessageWithAttachment>>? {
        return messages
    }

}