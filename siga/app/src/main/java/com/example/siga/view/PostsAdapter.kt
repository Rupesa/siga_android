package com.example.siga.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.siga.R
import com.example.siga.viewmodel.Post
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PostsAdapter (private val postList: ArrayList<Post>) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.post_item,
            parent, false)

        return PostsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {

        val post : Post = postList[position]
        holder.tvUserName.text = post.username
        holder.tvTitle.text = post.description

        if(post.timestamp != null){
            holder.tvDate.text = dateFormatter(post.timestamp!!.toDate())
        }

        holder.tvLocation.text = post.location

        Glide.with(holder.itemView).load(post.userUrl).into(holder.ivProfile)
        Glide.with(holder.itemView).load(post.mediaUrl).into(holder.ivImage)

    }

    override fun getItemCount(): Int {
        return postList.size
    }

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvUserName : TextView = itemView.findViewById(R.id.tvUserName)
        val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        val tvLocation : TextView = itemView.findViewById(R.id.tvLocation)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)

        val ivProfile : ImageView = itemView.findViewById(R.id.ivUserImage)
        val ivImage : ImageView = itemView.findViewById(R.id.ivImage)

    }

    private fun dateFormatter(d: Date) : String {
        return SimpleDateFormat("dd/MM/yyyy HH:mm").format(d).toString()
    }

}