package com.dcmobile.messenger7egend

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dcmobile.messenger7egend.db.Attachment
import com.dcmobile.messenger7egend.util.CircleTransformation
import com.squareup.picasso.Picasso

class Adapter(private val context: Context) : RecyclerView.Adapter<BaseMessageViewHolder>() {
    private val VIEW_TYPE_MY_MESSAGE = 1
    private val VIEW_TYPE_OTHER_MESSAGE = 2
    private val items = ArrayList<MessageWithAttachment>()

    fun addData(messages: List<MessageWithAttachment>) {
        items.clear()
        items.addAll(messages)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].user?.id == 1L) {
            VIEW_TYPE_MY_MESSAGE
        } else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMessageViewHolder{
        return when (viewType) {
            VIEW_TYPE_MY_MESSAGE -> {
                MyMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.my_message_item, parent, false))
            }

            else -> {
                OtherMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.other_message_item, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: BaseMessageViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            VIEW_TYPE_MY_MESSAGE, VIEW_TYPE_OTHER_MESSAGE -> {
                val message = items[position]
                holder.bind(message)
            }

        }
    }
}

abstract class BaseMessageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var llMessage: ViewGroup = view.findViewById(R.id.llMessage)
    var tvUserName: TextView = view.findViewById(R.id.tvUsername)
    var tvMessage: TextView = view.findViewById(R.id.tvMessage)
    var llAttachments: LinearLayout = view.findViewById(R.id.llAttachments)

    abstract fun bind(message: MessageWithAttachment)

    fun displayAttachments(attachmentResps: List<Attachment>) {
        llAttachments.visibility = View.VISIBLE
        llAttachments.removeAllViews()

        for (attachment in attachmentResps) {
            val view = LayoutInflater.from(view.context).inflate(R.layout.attachment_item, llMessage, false)

            Picasso.get()
                    .load(attachment.url)
                    .into(view.findViewById<ImageView>(R.id.ivAttachment))

            view.findViewById<TextView>(R.id.tvFilename).text = attachment.title

            llAttachments.addView(view)
        }
    }
}

class MyMessageViewHolder(view: View) : BaseMessageViewHolder(view) {
    val gravity = Gravity.END


    override fun bind(message: MessageWithAttachment) {
        val messageData = message.message
        val attachments = message.attachments

        tvUserName.text = view.context.getString(R.string.me)
        tvUserName.gravity = gravity

        tvMessage.text = messageData?.content
        tvMessage.gravity = gravity

        llMessage.setBackgroundResource(R.drawable.my_message_background)

        if (attachments != null) {
            displayAttachments(attachments)
        }
    }
}

class OtherMessageViewHolder(view: View) : BaseMessageViewHolder(view) {
    val gravity = Gravity.START
    override fun bind(message: MessageWithAttachment) {

        val messageData = message.message
        val attachments = message.attachments
        val user = message.user

        tvUserName.text = user?.name
        tvUserName.gravity = gravity

        tvMessage.text = messageData?.content
        tvMessage.gravity = gravity

        llMessage.setBackgroundResource(R.drawable.my_message_background)

        if (attachments != null) {
            displayAttachments(attachments)
        }

        llMessage.setBackgroundResource(R.drawable.other_message_background)

        Picasso.get()
                .load(message.user?.avatarId)
                .transform(CircleTransformation())
                .into(view.findViewById<ImageView>(R.id.ivUserProfile))
    }
}