package br.com.anderltda.funapp.activity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.model.User

class UserItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun inflate(parent: ViewGroup): UserItemViewHolder {
            return UserItemViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_item, parent, false))
        }
    }

    var name: TextView = itemView.findViewById(R.id.tv_user_name)

//    var textSubtitle: TextView = itemView.findViewById(R.id.text_subtitle)

 //   var textOther: TextView = itemView.findViewById(R.id.text_other)

 //   var buttonDelete: View = itemView.findViewById(R.id.button_delete)

//    var buttonUp: View = itemView.findViewById(R.id.button_up)

    fun bind(user: User) {

        name.text = user.name

    }
}