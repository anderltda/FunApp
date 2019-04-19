package br.com.anderltda.funapp.activity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.adapter.ChatData

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun inflate(parent: ViewGroup, position: Int): ItemViewHolder {
            when (position) {
                1 -> {
                    return ItemViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_holder_you, parent, false))
                } else -> {
                    return ItemViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_holder_me, parent, false))
                }
            }
        }
    }


    var message: TextView = itemView.findViewById(R.id.tv_chat_text)

    var time: TextView = itemView.findViewById(R.id.tv_time)

    //var name: TextView = itemView.findViewById(R.id.text_other)

    //var buttonDelete: View = itemView.findViewById(R.id.button_delete)

    //var buttonUp: View = itemView.findViewById(R.id.button_up)*/

    fun bind(chatData: ChatData) {
        //name.text = chatData.name
        time.text = chatData.time
        message.text = chatData.message

    }
}