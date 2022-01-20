package com.example.siga

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class EventsAdapter (private val eventList: ArrayList<Event>) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.EventsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_item,
        parent, false)

        return EventsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventsAdapter.EventsViewHolder, position: Int) {

        val event : Event = eventList[position]
        holder.tvUserName.text = event.userName
        holder.tvTitle.text = event.title
        holder.tvDate.text = event.date
        holder.tvLocation.text = event.location

        Glide.with(holder.itemView).load(event.userImage).into(holder.ivProfile)

    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    public class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvUserName : TextView = itemView.findViewById(R.id.tvUserName)
        val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        val tvLocation : TextView = itemView.findViewById(R.id.tvLocation)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)

        val ivProfile : ImageView = itemView.findViewById(R.id.ivProfile)
        val ivCheck : ImageView = itemView.findViewById(R.id.ivCheck)
        val ivImage : ImageView = itemView.findViewById(R.id.ivImage)

    }
}