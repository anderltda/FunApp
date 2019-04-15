package br.com.anderltda.funapp.activity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.model.State

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun inflate(parent: ViewGroup): ItemViewHolder {
            return ItemViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item, parent, false))
        }
    }

    var message: TextView = itemView.findViewById(R.id.tv_chat_text)

/*    var textSubtitle: TextView = itemView.findViewById(R.id.text_subtitle)

    var textOther: TextView = itemView.findViewById(R.id.text_other)

    var buttonDelete: View = itemView.findViewById(R.id.button_delete)

    var buttonUp: View = itemView.findViewById(R.id.button_up)*/

    fun bind(state: State) {

        //message.text = state.name

        message.text = state.message

        //message.text = state.population.toString()
    }
}