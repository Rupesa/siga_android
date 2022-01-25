package com.example.siga.view

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.siga.R
import com.example.siga.viewmodel.AppViewModel
import com.example.siga.viewmodel.Event
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class EventsAdapter (private val eventList: ArrayList<Event>, private val homeActivity: HomeActivity) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    private lateinit var name: String
    private lateinit var viewModel: AppViewModel
    private lateinit var db: FirebaseFirestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.event_item,
        parent, false)

        name = homeActivity.userName
        db = FirebaseFirestore.getInstance()

        return EventsViewHolder(itemView)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {

        val event : Event = eventList[position]
        holder.tvUserName.text = event.username
        holder.tvTitle.text = event.description
        if (event.timestamp == null) {
            holder.tvDate.text = ""
        } else {
            holder.tvDate.text = dateFormatter(event.timestamp!!.toDate())
        }
        if (event.location == null) {
            holder.tvLocation.text = ""
        } else {
            holder.tvLocation.text = event.location
        }
        holder.tvPeople.text = event.people?.size.toString()

       // Glide.with(holder.itemView).load(event.userUrl).into(holder.ivProfile)
        Glide.with(holder.itemView).load(event.mediaUrl).into(holder.ivImage)

        if (event.people?.contains(name) == true) {
            holder.ivCheck.setColorFilter(
                ContextCompat.getColor(
                    holder.ivCheck.context,
                    R.color.blue
                ), android.graphics.PorterDuff.Mode.SRC_IN
            );
        } else {
            holder.ivCheck.setColorFilter(
                ContextCompat.getColor(
                    holder.ivCheck.context,
                    R.color.grey
                ), android.graphics.PorterDuff.Mode.SRC_IN
            );
        }

        holder.ivCheck.setOnClickListener {
            var people : List<String> = emptyList()
            if (event.people?.contains(name) == true) {
                for (person in event.people!!) {
                    if (person != name) {
                        people += person
                    }
                }
            } else {
                people = event.people!!
                people += name
            }

            updatePeopleInEvent(event.eventId!!, people)
        }

    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    public class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvUserName : TextView = itemView.findViewById(R.id.tvUserName)
        val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        val tvLocation : TextView = itemView.findViewById(R.id.tvLocation)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)
        val tvPeople : TextView = itemView.findViewById(R.id.tvPeople)

        val ivProfile : ImageView = itemView.findViewById(R.id.ivUserImage)
        val ivCheck : ImageView = itemView.findViewById(R.id.ivCheck)
        val ivImage : ImageView = itemView.findViewById(R.id.ivImage)

    }

    fun dateFormatter(d: Date) : String {
        return SimpleDateFormat("dd/MM/yyyy HH:mm").format(d).toString()
    }

    fun updatePeopleInEvent(eventId : String, people: List<String>){
        db.collection("events")
            .document(eventId)
            .update("people", people);
    }

}