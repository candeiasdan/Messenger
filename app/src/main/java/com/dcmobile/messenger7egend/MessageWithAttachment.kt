package com.dcmobile.messenger7egend

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.dcmobile.messenger7egend.db.Attachment
import com.dcmobile.messenger7egend.db.Message
import com.dcmobile.messenger7egend.db.User


class MessageWithAttachment {
    @Embedded
    var message: Message? = null

    @Relation(parentColumn = "messageId", entityColumn = "fk_messageId")
    var attachments: List<Attachment>? = null

    @Embedded
    var user: User? = null
}
