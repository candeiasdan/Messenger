package com.dcmobile.messenger7egend.db

import android.arch.persistence.room.*
import android.support.annotation.NonNull
import android.support.v7.util.DiffUtil


@Entity(tableName = "Message",
        foreignKeys = [ForeignKey(entity = User::class, parentColumns = arrayOf("userId"), childColumns = arrayOf("fk_userId"))])
data class Message(
        @PrimaryKey
        @ColumnInfo(name = "messageId")
        var id: Long = Long.MAX_VALUE,

        @ColumnInfo(name = "fk_userId")
        var userId: Long = Long.MAX_VALUE,
        var content: String = "",

        //Used for displaying data
        @Ignore
        var attachments: List<Attachment>? = null
)

@Entity(tableName = "Attachment",
        foreignKeys = [ForeignKey(entity = Message::class, parentColumns = arrayOf("messageId"), childColumns = arrayOf("fk_messageId"))])
data class Attachment(
        @PrimaryKey
        @ColumnInfo(name = "attachmentId")
        var id: String = "",
        @ColumnInfo(name = "fk_messageId")
        var messageId: Long = Long.MAX_VALUE,
        var title: String = "",
        var url: String = ""
)

@Entity(tableName = "User")
data class User(
        @PrimaryKey
        @ColumnInfo(name = "userId")
        var id: Long = Long.MAX_VALUE,
        var name: String = "",
        var avatarId: String = ""
)