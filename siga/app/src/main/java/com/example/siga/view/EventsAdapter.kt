package com.example.siga.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.siga.R
import com.example.siga.viewmodel.Event
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class EventsAdapter (private val eventList: ArrayList<Event>) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.event_item,
        parent, false)

        return EventsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {

        val event : Event = eventList[position]
        holder.tvUserName.text = event.username
        holder.tvTitle.text = event.description
        if(event.timestamp != null){
            holder.tvDate.text = dateFormatter(event.timestamp!!.toDate())
        }
        holder.tvLocation.text = event.location
        //holder.tvPeople.text

       // Glide.with(holder.itemView).load(event.userUrl).into(holder.ivProfile)
        Glide.with(holder.itemView).load(event.mediaUrl).into(holder.ivImage)

    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    public class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvUserName : TextView = itemView.findViewById(R.id.tvUserName)
        val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        val tvLocation : TextView = itemView.findViewById(R.id.tvLocation)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)

        val ivProfile : ImageView = itemView.findViewById(R.id.ivUserImage)
        val ivCheck : ImageView = itemView.findViewById(R.id.ivCheck)
        val ivImage : ImageView = itemView.findViewById(R.id.ivImage)

    }

    fun dateFormatter(d: Date) : String {
        return SimpleDateFormat("dd/MM/yyyy HH:mm").format(d).toString()
    }

}