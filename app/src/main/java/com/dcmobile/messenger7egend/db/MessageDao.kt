package com.dcmobile.messenger7egend.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.dcmobile.messenger7egend.MessageWithAttachment


@Dao
interface MessageDao {
    // INSERT
    @Insert(onConflict = REPLACE)
    fun saveMessage(message: Message)

    @Insert(onConflict = REPLACE)
    fun saveUser(user: User)

    @Insert(onConflict = REPLACE)
    fun saveAttachment(attachment: Attachment)

    // SELECT
    @Query("SELECT * from Message INNER JOIN User ON fk_userId = userId")
    fun getMessagesWithAttachments(): LiveData<List<MessageWithAttachment>>

    // COUNT
    @Query("SELECT COUNT(*) FROM Message")
    fun getMessageCount(): Int
}