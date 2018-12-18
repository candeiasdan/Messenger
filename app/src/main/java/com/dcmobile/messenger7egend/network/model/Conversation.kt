package com.dcmobile.messenger7egend.network.model

import com.dcmobile.messenger7egend.db.Message
import com.dcmobile.messenger7egend.db.User

class Conversation(
        val messages: List<Message>,
        val users: List<User>
)