package br.com.anderltda.funapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import br.com.anderltda.funapp.R

class HolderMe(v: View) : RecyclerView.ViewHolder(v) {

    var time: TextView? = null
    var chatText: TextView? = null

    init {
        time = v.findViewById(R.id.tv_time) as TextView
        chatText = v.findViewById(R.id.tv_chat_text) as TextView
    }
}