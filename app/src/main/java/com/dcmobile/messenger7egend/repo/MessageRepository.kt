package com.dcmobile.messenger7egend.repo

import android.arch.lifecycle.LiveData
import android.widget.Toast
import com.dcmobile.messenger7egend.App
import com.dcmobile.messenger7egend.MessageWithAttachment
import com.dcmobile.messenger7egend.db.Attachment
import com.dcmobile.messenger7egend.db.MessageDao
import com.dcmobile.messenger7egend.network.HostService
import com.dcmobile.messenger7egend.network.model.Conversation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor


class MessageRepository(private val webservice: HostService, private val messageDao: MessageDao, private val executor: Executor) {

    fun getMessages(): LiveData<List<MessageWithAttachment>> {
        loadFromNetworkIfNeeded()
        return messageDao.getMessagesWithAttachments()
    }

    private fun loadFromNetworkIfNeeded() {
        executor.execute {
            // Check if messages were fetched
            val isDatabaseComplete = messageDao.getMessageCount() > 0

            // In case we need to download data
            if (!isDatabaseComplete) {
                webservice.getConversation().enqueue(object : Callback<Conversation> {

                    override fun onResponse(call: Call<Conversation>, response: Response<Conversation>) {
                        Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show()

                        executor.execute {
                            val conversation = response.body()

                            //Store messages from Network call on local database
                            if (conversation != null) {

                                //store users
                                conversation.users.map { messageDao.saveUser(it) }

                                //store messages
                                conversation.messages.map { messageDao.saveMessage(it) }

                                //store attachments with updated messageId
                                for (message in conversation.messages) {
                                    message.attachments?.forEach { messageDao.saveAttachment(Attachment(it.id, message.id, it.title, it.url)) }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<Conversation>, t: Throwable) {
                        Toast.makeText(App.context, "Error loading from network !", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}