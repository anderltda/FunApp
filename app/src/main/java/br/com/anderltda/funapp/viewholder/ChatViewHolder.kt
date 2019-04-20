package br.com.anderltda.funapp.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.model.Chat

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun inflate(parent: ViewGroup, position: Int): ChatViewHolder {
            when (position) {
                1 -> {
                    return ChatViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_chat_you, parent, false)
                    )
                } else -> {
                    return ChatViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_chat_me, parent, false)
                    )
                }
            }
        }
    }


    var message: TextView = itemView.findViewById(R.id.tv_chat_text)

    var time: TextView = itemView.findViewById(R.id.tv_time)

    //var name: TextView = itemView.findViewById(R.id.text_other)

    //var buttonDelete: View = itemView.findViewById(R.id.button_delete)

    //var buttonUp: View = itemView.findViewById(R.id.button_up)*/

    fun bind(chat: Chat) {
        //name.text = chat.name
        time.text = chat.time
        message.text = chat.message

    }
}