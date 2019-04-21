package br.com.anderltda.funapp.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.model.ContactLocation

class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun inflate(parent: ViewGroup): LocationViewHolder {
            return LocationViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_contact_location, parent, false)
            )
        }
    }

    var name: TextView = itemView.findViewById(R.id.tv_user_name)


 //   var textOther: TextView = itemView.findViewById(R.id.text_other)

 //   var buttonDelete: View = itemView.findViewById(R.id.button_delete)

//    var buttonUp: View = itemView.findViewById(R.id.button_up)

    fun bind(contactLocation: ContactLocation) {
        name.text = contactLocation.name
    }
}